let map, infoWindow;
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 37.566672, lng: 126.8784},
        zoom: 16
    });

    map.addListener('dragend', ()=> {
        draggedCenterLocation = map.mapUrl.substring(map.mapUrl.indexOf('ll=')+3,map.mapUrl.indexOf('&z='));
        getMasksInfomation(draggedCenterLocation.split(',')[0],draggedCenterLocation.split(',')[1],1000);
    });


    infoWindow = new google.maps.InfoWindow;

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            const pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            infoWindow.setPosition(pos);
            map.setCenter(pos);
        }, function() {
            handleLocationError(true, infoWindow, map.getCenter());
        });
    } else {
        // Browser doesn't support Geolocation
        handleLocationError(false, infoWindow, map.getCenter());
    }
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    // infoWindow.setPosition(pos);
    // infoWindow.setContent(browserHasGeolocation ?
    //     'Error: The Geolocation service failed.' :
    //     'Error: Your browser doesn\'t support geolocation.');
    // infoWindow.open(map);
}

function getMasksInfomation(lat,lng,m){
    axios.get(`https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByGeo/json?lat=${lat}&lng=${lng}&m=${m}`)
        .then((response)=>{
            drawMarkers(response.data);
        });
}


let markers = [];
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
            animation: google.maps.Animation.DROP,
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
        markers.push(marker);
    }
}


function addressSearch(){
    const geocoder = new google.maps.Geocoder();
    geocoder.geocode({'address':$("#search-address").val()}, function(result, status) {
        console.log(result[0].geometry);
        const pos = {
            lat: result[0].geometry.location.lat(),
            lng: result[0].geometry.location.lng()
        };
        if(status === 'OK'){
            getMasksInfomation(result[0].geometry.location.lat(),result[0].geometry.location.lng(),1000);
            infoWindow.setPosition(pos);
            map.setCenter(pos);
        }
    });
}


function getLocation() {
    console.log(navigator.geolocation);
    if (navigator.geolocation) { // GPS를 지원하면
        navigator.geolocation.getCurrentPosition(function(position) {
            getMasksInfomation(position.coords.latitude,position.coords.longitude,1000);
            console.log(position.coords.latitude);
            console.log(position.coords.longitude);
        }, function(error) {
            console.error(error);
        }, {
            enableHighAccuracy: false,
            maximumAge: 0,
            timeout: Infinity
        });
    } else {
        alert('GPS를 지원하지 않습니다');
    }
}

getLocation();