document.getElementById('forgotPasswordForm').addEventListener('submit', function(e) {
    const loginKey = document.getElementById('loginKey').value;
    const messageSpan = document.getElementById('message');
    const btn = document.getElementById('btnRequest');

    btn.innerText = "Sending...";
    btn.disabled = true;

    fetch('/requestResetToken', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ loginKey: loginKey })
    })
    .then(response => response.json())
    .then(data => {
        // Giả sử Controller trả về TokenResponseDTO có chứa token
        messageSpan.innerText = "Check your email for the reset link!";
        messageSpan.style.color = "green";
        console.log("Token generated:", data.token); // Chỉ log để test, thực tế sẽ gửi qua mail
    })
    .catch((error) => {
        messageSpan.innerText = "Something went wrong. Please try again.";
        messageSpan.style.color = "red";
        btn.disabled = false;
        btn.innerText = "Send Reset Link";
    });
});