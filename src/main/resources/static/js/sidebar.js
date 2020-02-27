getPopularPosts = () => {
    $.ajax({
        url: "/api/posts/popular-list",
        method: "get",
        contentType: "application/json;charset=UTF-8",
        success: function (data) {
            drawPopularContent(data);
        },
        error: function (e) {
            console.log("error:", e);
        }
    });
};




drawPopularContent = (data) => {
    let popularContainer = $("#popular-post-container");
    $.each(data, (i, item) => {
        popularContainer.append(`
            <li>
              <a href="/posts/${item.id}">
                <div class="text">
                  <h4>${item.title}</h4>
                  <div class="post-meta">
                    <span class="mr-2">${item.createdTime} </span>
                  </div>
                </div>
              </a>
            </li>
        `)
    });
};


getPopularPosts();