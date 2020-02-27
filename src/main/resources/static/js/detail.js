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
            <li class="comment">
                <div class="comment-body">
                    <h3>Jean Doe</h3>
                    <div class="meta">January 9, 2018 at 2:21pm</div>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Pariatur quidem laborum
                        necessitatibus, ipsam impedit vitae autem, eum officia, fugiat saepe enim
                        sapiente iste iure! Quam voluptas earum impedit necessitatibus, nihil?</p>
                    <p><a href="#" class="reply rounded">Reply</a></p>
                </div>
            </li>
        `);
    }
    console.log(data);
};


getPost($("#post-id").val());