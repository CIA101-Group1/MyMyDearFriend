function checkAuthorization(authorization) {
    if (null === authorization) {
        window.location.href = "/mmdf/web/api/member/login";
    }
}