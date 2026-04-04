const signupForm = document.getElementById("signupForm");

signupForm.addEventListener('submit', (event) => {
    // Ngăn form load lại trang
    event.preventDefault();

    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirm').value;

    // 1. Kiểm tra khớp mật khẩu tại Frontend
    if (password !== confirmPassword) {
        alert("Mật khẩu xác nhận không khớp!");
        return;
    }

    const data = {
        username: username,
        email: email,
        password: password
    };

    // 2. Gửi Fetch tới Server
    fetch('/req/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(async response => {
        const result = await response.json();

        if (result.success) {
            alert("Đăng ký thành công!");
            signupForm.reset();
        } else {
            alert("Lỗi: " + result.message);
        }
    })
    .catch(error => {
        console.error("Lỗi kết nối:", error);
        alert("Không thể kết nối tới Server Java!");
    });
});