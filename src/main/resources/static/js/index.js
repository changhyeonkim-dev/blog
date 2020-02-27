let page = 0;
let category = "home";
getPosts = (requestCategory) => {
    if(requestCategory === "home"){
        $.ajax({
            url: "/api/posts?page=" + page,
            method: "get",
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                console.log("data:", data);
                drawMainContent(data);
            },
            error: function (e) {
                console.log("error:", e);
            }
        });
        return;
    }
    $.ajax({
        url: "/api/posts/categories/"+category+"?page=" + page,
        method: "get",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            console.log("data:", data);
            drawMainContent(data);
        },
        error: function (e) {
            console.log("error:", e);
        }
    });
};


drawMainContent = (data) => {
    let contentContainer = $("#content-container");
    $.each(data, (i, item) => {
        contentContainer.append(`
            <div class="col-md-12">
                  <div class="post-entry-horzontal" style="text-align: center;">
                    <a href="/posts/${item.id}" style="width: 100%;">
                    <div class="image element-animate fadeIn element-animated" data-animate-effect="fadeIn" style="background-image: url(${item.previewImagePath==null || item.previewImagePath===""?'images/noimage.gif':item.previewImagePath});"></div>
                      <span class="text">
                        <div class="post-meta">
                          <span class="author mr-2"> ${item.account.userId} </span>&bullet;
                          <span class="mr-2"> ${item.createdTime} </span> &bullet;
                          <span class="ml-2"><span class="fa fa-comments"></span> ${item.comments.length} </span>
                        </div>
                        <h2>${item.title}</h2>
                      </span>
                    </a>
                  </div>
                </div>
        `)
    });
};


$(window).scroll(function () {
    // $(window).scrollTop(); //스크롤의 현재 위치
    // $(document).height() //도큐먼트 높이로 고정
    // $(window).height() //윈도우창 높이 가변
    if ($(window).scrollTop() === $(document).height() - $(window).height()) {
        page++;
        getPosts(category);
    }
});

$("#a-home").on("click",(e)=> {
    page=0;
    category = "home";
    e.preventDefault();
    $("#content-container").html('');
    getPosts(category);
});

$("#a-develop").on("click",(e)=> {
    page=0;
    category = "develop";
    e.preventDefault();
    $("#content-container").html('');
    getPosts(category);
});

$("#a-picture").on("click",(e)=> {
    page=0;
    category = "pictures";
    e.preventDefault();
    $("#content-container").html('');
    getPosts(category);
});

getPosts(category);