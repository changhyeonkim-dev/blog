let map;
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 37.566672, lng: 126.8784},
        zoom: 16
    });

    map.addListener('dragend', ()=> {
        getMasksInfomation(map.getCenter().lat(),map.getCenter().lng(),1000);
    });


    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            const pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            map.setCenter(pos);
        })
    }
}


function getMasksInfomation(lat,lng,m){
    axios.get(`https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByGeo/json?lat=${lat}&lng=${lng}&m=${m}`)
        .then((response)=>{
            drawMarkers(response.data);
        });
}


let markers = [];
let infowindows = [];
function drawMarkers(data){
    console.log(data);
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    for (let i = 0; i < data.stores.length; i++) {
        let maskAmount ='';
        let color = '';
        if(data.stores[i].remain_stat ==='plenty'){
            maskAmount = '100개 이상'
            color = 'green';
        }else if(data.stores[i].remain_stat ==='some'){
            maskAmount = '30개 이상 100개미만'
            color = 'blue';
        }else if(data.stores[i].remain_stat ==='few'){
            maskAmount = '2개 이상 30개 미만'
            color = 'yellow';
        }else{
            maskAmount = '재고 없음'
            color = 'red';
        }
        let url = "http://maps.google.com/mapfiles/ms/icons/";
        url += color + "-dot.png";


        const marker = new google.maps.Marker({
            map: map,
            position: {lat:data.stores[i].lat,lng:data.stores[i].lng},
            icon:{
                url : url
            }
        });

        const infowindow = new google.maps.InfoWindow({
            content: `
            주소:${data.stores[i].addr} <br>
            이름: ${data.stores[i].name} <br>
            재고: ${maskAmount} <br>
            판매 시작시간:${data.stores[i].stock_at} <br>
            최종 업데이트 시간:${data.stores[i].created_at}<br>
            `
        });

        marker.addListener('click', function() {
            infowindow.open(map, marker);
        });
        infowindows.push(infowindow);
        markers.push(marker);
    }
}


function addressSearch(){
    const geocoder = new google.maps.Geocoder();
    geocoder.geocode({'address':$("#search-address").val()}, function(result, status) {
        const pos = {
            lat: result[0].geometry.location.lat(),
            lng: result[0].geometry.location.lng()
        };
        if(status === 'OK'){
            getMasksInfomation(result[0].geometry.location.lat(),result[0].geometry.location.lng(),1000);
            map.setCenter(pos);
        }
    });
}


function getCurrentLocation() {
    navigator.geolocation.getCurrentPosition((pos)=>{
        getMasksInfomation(pos.coords.latitude,pos.coords.longitude,1000);
        map.setCenter({lat:pos.coords.latitude,lng:pos.coords.longitude});
    });
}

getCurrentLocation();