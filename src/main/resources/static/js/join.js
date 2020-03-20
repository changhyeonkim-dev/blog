$("#form-signup").submit((e) => {
    e.preventDefault();

    const userId = $("#userId").val();
    const password = $("#password").val();
    const password2 = $("#password2").val();

    let form = new FormData();
    form.append("userId", userId);
    form.append("password", password);
    if (validationForm(userId, password, password2)) {
        axios.post(`/api/account`, form, {headers: {'Content-type': 'application/x-www-form-urlencoded'}}).then()
            .then(response => {
            console.log(response);
            alert("회원가입에 성공했습니다 이메일을 통해 인증 후 사용해주세요!");
            history.back();
            })
            .catch(response => {
                console.log(response);
                alert(response.response.data.errors[0].code);
            });
    }
});

function validationForm(userId, password, password2) {
    const regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    if (password !== password2 && password.length > 3) {
        alert('패스워드를 확인해주세요!');
        return false;
    }
    if (userId.match(regExp) === null) {
        alert('이메일 형식을 확인해주세요');
        return false;
    }
    return true;

}
