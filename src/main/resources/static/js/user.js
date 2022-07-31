let index = {
    init: function () {
        $("#btn-save").on("click", () => {
            this.save();
        });
        $("#btn-update").on("click", () => {
            this.update();
        });
    },

    save: function () {
        let data = {
            username: $("#username").val(),
            email: $("#email").val(),
            password: $("#password").val()
        }
        $.ajax({
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (response) {
            if (response.status == 500) {
                alert('아이디가 존재합니다!');
            } else {
                alert('회원가입완료!');
                location.href = "/";
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },

    update: function () {
        let data = {
            id: $("#id").val(),
            username: $("#username").val(),
            email: $("#email").val(),
            password: $("#password").val()
        }
        $.ajax({
            type: "PUT",
            url: "/user",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (response) {
            alert('회원수정이 완료되었어요!');
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },

}
index.init();
