const loginForm = document.getElementById("form");

loginForm.addEventListener('submit', (event) => {
    event.preventDefault(); 

    // Lấy giá trị từ ô nhập liệu (ô này giờ dùng chung cho cả User và Email)
    const loginKey = document.getElementById('loginKey').value; 
    const password = document.getElementById('password').value;

    const data = {
        username: loginKey,
        password: password
    };

    fetch('/req/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(async response => {
        if (response.ok) {
            // Lưu giá trị đăng nhập vào session
            sessionStorage.setItem("currentUser", loginKey);
            
            alert("Đăng nhập thành công!");
            window.location.href = "/online-order"; // chuyen tam sang order luon de test
        } else {
            const errorMsg = await response.text();
            alert("Thất bại: " + (errorMsg || "Thông tin đăng nhập không chính xác!"));
        }
    })
    .catch(error => {
        console.error("Lỗi:", error);
        alert("Không thể kết nối tới Server!");
    });
});