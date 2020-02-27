$(document).ready(function () {
    $('#summernote').summernote({
        height: 300,                 // 에디터 높이
        minHeight: null,             // 최소 높이
        maxHeight: null,             // 최대 높이
        focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR",				// 한글 설정
        callbacks: {
            onImageUpload: (files, editor, welEditable) => {

            }
        }
    });
});
$("#btn-save").on("click", (e) => {
    e.preventDefault();
    let data = {};
    data["title"] = $("#title").val();
    data["contents"] = $('#summernote').summernote('code');
    $.ajax({
        url: "/api/posts",
        method: "post",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(data),
        success: function (result) {
            console.log("result:", result);
        },
        error: function (e) {
            console.log("error:", e);
        }
    });
});