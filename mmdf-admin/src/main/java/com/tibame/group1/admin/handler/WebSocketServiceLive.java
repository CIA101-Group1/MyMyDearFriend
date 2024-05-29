package com.tibame.group1.admin.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tibame.group1.admin.dto.*;
import com.tibame.group1.admin.service.JwtService;
import com.tibame.group1.db.entity.ServiceChatroomEntity;
import com.tibame.group1.db.repository.ServiceChatroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebSocketServiceLive implements WebSocketHandler {
    // ----------- 會員
    private ConcurrentMap<Integer, WebSocketSession> memberSessionMap = new ConcurrentHashMap<>();
    private ConcurrentMap<WebSocketSession, Integer> memberIdMap= new ConcurrentHashMap<>();
    // ----------- 會員排隊號碼牌
    private List<Integer> memberNumber = Collections.synchronizedList(new ArrayList<>());
    // ----------- 客服
    private ConcurrentMap<Integer, WebSocketSession> services = new ConcurrentHashMap<>();
    // ----------- 客服服務人數
    private ConcurrentMap<WebSocketSession, Integer> servicesId = new ConcurrentHashMap<>();

    private ConcurrentMap<Integer, Integer> memberGetServicId = new ConcurrentHashMap<>();

    private Gson gson = new Gson();
    @Autowired private JwtService jwtService;

    @Autowired private ServiceChatroomRepository serviceChatroomRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {}

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
        JsonObject jsonObj = gson.fromJson(message.getPayload().toString(), JsonObject.class);
        String type = jsonObj.get("type").getAsString();
        System.out.println(type);
        // ============ 會員進入聊天室 ============//
        if ("member".equals(type)) {
            Integer memberId = jsonObj.get("memberId").getAsInt();
            memberSessionMap.put(memberId,session);
            memberIdMap.put(session,memberId);
            memberNumber.add(memberId);

            System.out.println("會員編號：" + memberId + " -> 已進入前台客服聊天室");
            ServiceLiveResDTO dto = new ServiceLiveResDTO();
            for(int i =0 ;i<1 ;){
                Set<WebSocketSession> keys = servicesId.keySet();
                if(keys != null){
                    for (WebSocketSession key : keys){
                        dto.setServiceId(servicesId.get(key));
                        i=2;
                        break;
                    }

                }
            }
            dto.setType("getServiceId");
            session.sendMessage(new TextMessage(gson.toJson(dto)));
        }
        // ============ 員工進入聊天室 ============//
        if ("service".equals(type)) {
            String authorization = jsonObj.get("authorization").getAsString();
            AdminLoginSourceDTO dto = jwtService.decodeLogin(authorization);
            System.out.println("員工編號：" + dto.getEmployeeId() + " -> 已進入後台客服聊天室");
            services.put(dto.getEmployeeId(),session);
            servicesId.put(session,dto.getEmployeeId());
            Map<String,String> init = new HashMap<>();
            init.put("type","init");
            init.put("serviceId",servicesId.get(session).toString());
            session.sendMessage(new TextMessage(gson.toJson(init)));
        }

        if ("service_getCustomer".equals(type)) {
            if (servicesId.containsKey(session)) {
                SerivceMemberSouerDTO memberSouerDTO = new SerivceMemberSouerDTO();
                List<WaitingCustomersDTO> membersList = new ArrayList<>();
                for(Integer id : memberNumber){
                    WaitingCustomersDTO customersDTO = new WaitingCustomersDTO();
                    System.out.println(serviceChatroomRepository.findLastMessage(id));
                    customersDTO.setLsatMessage(serviceChatroomRepository.findLastMessage(id));
                    customersDTO.setId(id);
                    membersList.add(customersDTO);
                }
                System.out.println("取得會員資訊 - 成功");

                memberSouerDTO.setMemberIdList(membersList);
                memberSouerDTO.setType("getCustomer");
                session.sendMessage(new TextMessage(gson.toJson(memberSouerDTO)));
                return;
            }
            System.out.println("取得會員資訊 - 失敗");
            return;
        }
        if ("member_message".equals(type)) {
            ServiceMessageDTO messageDTO = serviceMessageDTO(jsonObj,jsonObj.get("memberId").getAsString());
            saveSQLData(messageDTO);
            messageDTO.setType("member_message");
            WebSocketSession serviceSession = services.get(messageDTO.getServiceId());
            serviceSession.sendMessage(new TextMessage(gson.toJson(messageDTO)));
            System.out.println("會員正在傳送資料");
            return;
        }
        if ("service_message".equals(type)) {
            System.out.println(jsonObj.get("serviceId").getAsString());
            ServiceMessageDTO messageDTO = serviceMessageDTO(jsonObj ,jsonObj.get("serviceId").getAsString());
            saveSQLData(messageDTO);
            messageDTO.setType("service_message");
            WebSocketSession serviceSession = memberSessionMap.get(messageDTO.getMemberId());
            serviceSession.sendMessage(new TextMessage(gson.toJson(messageDTO)));
            System.out.println("客服正在傳送資料");
            return;
        }
        if ("getChatHistory".equals(type)) {
            Integer memberId = jsonObj.get("customerId").getAsInt();
            Integer serviceId = jsonObj.get("serviceId").getAsInt();
            List<ServiceChatroomEntity> list = serviceChatroomRepository.findHistoryMessage(memberId,serviceId);
            List<ServiceMessageDTO> dtoRes = new ArrayList<>();
            for(ServiceChatroomEntity ent : list){
                ServiceMessageDTO dto = new ServiceMessageDTO();

                dto.setMessage(ent.getMessage());
                dto.setMessage_type(ent.getType());
                dto.setServiceId(ent.getServiceId());
                dto.setMemberId(ent.getCustomerId());
                dto.setDate(ent.getDate());
                dtoRes.add(dto);

                System.out.println(ent.getMessage());
                System.out.println(ent.getDate());

            }
            String souerDto = gson.toJson(dtoRes);
            Map<String,String> resp = new HashMap<>();
            resp.put("type","getChatHistory");
            resp.put("memberMessageList",souerDto);
//            SerivceMemberSouerDTO souerDTO = new SerivceMemberSouerDTO();
//            souerDTO.setMemberIdList(dtoRes);
//            souerDTO.setType("getChatHistory");
            session.sendMessage(new TextMessage(gson.toJson(resp)));
        return;
        }
        System.out.println("沒有方法");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
            throws Exception {
        if(memberIdMap.containsKey(session)){
            Integer memberId = memberIdMap.get(session);
            memberNumber.remove(memberId);
            memberSessionMap.remove(memberId);
            memberIdMap.remove(session);
            return;
        }
        if (servicesId.containsKey(session)){
            Integer serviceId = servicesId.get(session);
            servicesId.remove(session);
            services.remove(serviceId);
            return;
        }

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void saveSQLData(ServiceMessageDTO messageDTO) {
        ServiceChatroomEntity messageEnt = new ServiceChatroomEntity();
        messageEnt.setMessage(messageDTO.getMessage());
        messageEnt.setCustomerId(messageDTO.getMemberId());
        messageEnt.setServiceId(messageDTO.getServiceId());
        messageEnt.setDate(messageDTO.getDate());
        messageEnt.setType(messageDTO.getMessage_type());
        messageEnt = serviceChatroomRepository.save(messageEnt);
    }

    private ServiceMessageDTO serviceMessageDTO(JsonObject jsonObj ,String type) {
        ServiceMessageDTO messageDTO = new ServiceMessageDTO();
        messageDTO.setMessage(jsonObj.get("message").getAsString());
        messageDTO.setServiceId(jsonObj.get("serviceId").getAsInt());
        messageDTO.setMemberId(jsonObj.get("memberId").getAsInt());
        messageDTO.setMessage_type(type);
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        messageDTO.setDate(time);
        return messageDTO;
    }

    // ------------ 預設分配器 ------------//
    private void distribute() {
        //  Set<WebSocketSession> set = services.keySet();

        //            WebSocketSession serivce = null;
        //            Integer number = 5;
        //            for (WebSocketSession webSocketSession : set) {
        //                if (serviceMembers.get(set) < number) {
        //                    service = webSocketSession;
        //                    number = services.get(set);
        //                }
        //            }
        //            if (service == null) {}

        //            services.get(service);
        //            ServiceLiveResDTO respDTO = new ServiceLiveResDTO();
        //            respDTO.setType("serviceId");
        //            respDTO.setServiceId(services.get(service));
        //            session.sendMessage(new TextMessage(gson.toJson(respDTO)));
    }
}
