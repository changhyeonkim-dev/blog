const axios = $.getScript( "https://unpkg.com/axios/dist/axios.min.js");

//axios.globalConfiguration
const token = document.cookie.split(';').filter((item)=>item.trim().startsWith('X-AUTH-TOKEN=')).toString().split('X-AUTH-TOKEN=')[1];
axios.defaults.headers.common = {
    "X-AUTH-TOKEN" : token,
    "X-Requested-With": "XMLHttpRequestt",
};