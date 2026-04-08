// Hiển thị hóa đơn + QR code thanh toán
async function showMockInvoiceWithQR(orderResponse, originalData, invoiceContent, invoiceSection, API_BASE_URL) {
    const dateStr = new Date().toLocaleString();
    const orderId = orderResponse.id || 'N/A';
    const orderCode = `ORDER_${orderId}`;
    
    // Dùng trực tiếp originalData.items
    let itemsHtml = originalData.items.map(item => `
        <div class="flex justify-between border-b border-dashed py-1">
            <span>${item.name} x ${item.quantity}</span>
            <span>${(item.price * item.quantity).toLocaleString('vi-VN')} đ</span>
        </div>
    `).join('');

    let qrCodeHtml = '';
    let qrDataURL = orderResponse.qrDataURL;

    if (originalData.methodPayment === 'VIETQR' && !qrDataURL) {
        try {
            const qrResponse = await fetch(
                `${API_BASE_URL}/api/qr-code/generate?orderId=${orderId}&totalAmount=${originalData.totalAmount}&orderCode=${encodeURIComponent(orderCode)}`
            );
            const qrJson = await qrResponse.json();
            qrDataURL = qrJson?.data?.qrDataURL || qrDataURL;
        } catch (err) {
            console.warn('Không thể tạo QR code:', err);
        }
    }

    if (originalData.methodPayment === 'VIETQR' && qrDataURL) {
        qrCodeHtml = `
            <div class="text-center mt-6 pt-4 border-t">
                <p class="text-sm font-bold mb-3">📲 Mã QR Thanh Toán</p>
                <img src="${qrDataURL}" 
                     alt="QR Code" 
                     class="w-48 h-48 mx-auto"
                     style="border: 2px solid #333; padding: 8px; background: white;">
                <p class="text-xs mt-3 text-gray-700 font-medium">
                    🏦 <strong>MBBank</strong> | <strong>0966113890</strong><br>
                    💰 <strong>${originalData.totalAmount.toLocaleString('vi-VN')} VNĐ</strong>
                </p>
            </div>
        `;
    }

    invoiceContent.innerHTML = `
        <div class="text-center mb-4">
            <h2 class="text-2xl font-bold">🧾 HÓA ĐƠN</h2>
            <p class="text-xs text-gray-600">ID: ${orderId}</p>
            <p class="text-xs text-gray-600">Bàn: ${originalData.tableId} | ${dateStr}</p>
        </div>
        <div class="space-y-1 font-mono text-sm mb-4">
            ${itemsHtml}
        </div>
        <div class="flex justify-between text-lg font-bold border-t pt-3 mb-2">
            <span>TỔNG TIỀN:</span>
            <span class="text-red-600">${originalData.totalAmount.toLocaleString('vi-VN')} đ</span>
        </div>
        ${qrCodeHtml}
    `;
    invoiceSection.classList.remove('hidden');
}
