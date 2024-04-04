const menu = () => {
        const aside = document.getElementById('aside');
           aside.style.display = 'block';
    }
    function join_btn() {
        if(fm.userid.value == "") {
            alert('아이디를 입력해주세요');
            fm.userid.focus();
            return false;
        }
        if(document.getElementById("id_ok").innerHTML == "중복된 아이디 입니다."){
            alert("아이디가 중복되었습니다.")
            fm.userid.focus();
            return false;
        }
        if(fm.pwd.value == "") {
            alert('비밀번호를 입력해주세요');
            fm.pwd.focus();
            return false;
        }
        if(fm.pwd2.value == "") {
            alert('비밀번호확인 입력해주세요');
            fm.pwd2.focus();
            return false;
        }
        if(fm.pwd.value != fm.pwd2.value) {
            alert('비밀번호가 일치하지 않습니다. 다시 입력해주세요');
            fm.pwd2.focus();
            return false;
        }
        if(fm.name.value == "") {
            alert('이름을 입력해주세요');
            fm.name.focus();
            return false;
        }
        if(fm.phone1.value == "") {
            alert('번호를 입력해주세요');
            fm.phone1.focus();
            return false;
        }
        if(fm.phone2.value == "") {
            alert('번호를 입력해주세요');
            fm.phone2.focus();
            return false;
        }
        if(fm.phone3.value == "") {
            alert('번호를 입력해주세요');
            fm.phone3.focus();
            return false;
        }
        if(fm.email.value == "") {
            alert('이메일을 입력해주세요');
            fm.email.focus();
            return false;
        }
    }