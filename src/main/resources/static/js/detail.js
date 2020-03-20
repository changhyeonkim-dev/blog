getPost = (id) => {
    $.ajax({
        url: "/api/posts/"+id,
        method: "get",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            drawContent(data);
        },
        error: function (e) {
            console.log("error:", e);
        }
    });
};


drawContent = (data) => {
    $("#post-created-date").html(data.createdTime);
    $("#post-comments-count").html('<span class="fa fa-comments"></span>&nbsp'+ data.comments.length);
    $("#comment-amount").html(data.comments.length !==0 ?data.comments.length+" 개의 댓글이 있습니다" : '');
    $("#post-title").html(data.title);
    $("#post-content-body").html(data.contents);
    for (let i = 0; i < data.comments.length; i++) {
        $("#comment-list").append(`
            <li id="comment" class="comment">
                <div class="comment-body">
                    <h3>${data.comments[i].account.userId}</h3>
                    <div class="meta">${data.comments[i].createdTime}</div>
                    <p>${data.comments[i].commentContents}</p>
                </div>
            </li>
        `);
    }
    console.log(data);
};

axios.get("/api/posts/"+$("#post-id").val())
    .then((response)=>{
        if(response.status!==200){
            alert('에러 발생');
            return;
        }
        drawContent(response.data);
    });

$("#div-reply-submit").on("click",(e)=>{
    e.preventDefault();
    if(replyValidation()){
        const token = document.cookie.split(';').filter((item)=>item.trim().startsWith('X-AUTH-TOKEN=')).toString().split('X-AUTH-TOKEN=')[1];
        axios.post(`/api/posts/${$("#post-id").val()}/reply`,{contents:$("#reply-text").val()},{ headers: {"X-AUTH-TOKEN" : `${token}`}}).then(res => {
            console.log(res.data)
        })
    }
});

function replyValidation(){
    const replyText = $("#reply-text").val();
    if(replyText.length <3){
        alert('3글자 이상 입력해주세요');
        return false;
    }
    return true;
}

getToken=() =>{
    const name = "X-AUTH-TOKEN";
    const value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value? value[2] : null;
};

if(getToken() === null){
    $("#reply-text").attr("readonly",true);
    $("#reply-text").on("click",()=>alert('로그인 후 사용 가능합니다'));
}