package com.tibame.group1.web.dto;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@CacheConfig(cacheNames = "member", keyGenerator = "mmdfKeyGenerator")
public class WalletBackendController {



    }

