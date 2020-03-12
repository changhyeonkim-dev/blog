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