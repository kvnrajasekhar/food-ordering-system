function updateOrderStatus(orderId, orderTime) {
    const orderCard = document.getElementById(orderId);
    if (!orderCard) return;

    const statusElement = orderCard.querySelector('.order-status');
    const etaElement = orderCard.querySelector('.order-eta');
    const now = new Date();
    const timeDiff = (now.getTime() - orderTime.getTime()) / 60000; // in minutes

    let status = '';
    let eta = '';

    if (timeDiff < 5) {
        status = 'Partner Yet to Assign';
        eta = '30 minutes';
    } else if (timeDiff < 10) {
        status = 'Partner Assigned';
        eta = '25 minutes';
    } else if (timeDiff < 20) {
        status = 'Taken Order / On the way';
        eta = '15 minutes';
    } else {
        status = 'Delivered';
        eta = 'Delivered';
    }

    statusElement.textContent = status;
    statusElement.className = `order-status ${status.toLowerCase().replace(/ /g, '_')}`;
    etaElement.textContent = ``;
    const icon = document.createElement('i');
    icon.className="fas fa-clock";
    etaElement.prepend(icon);
    etaElement.textContent += eta;

}

// Function to generate ETA based on status - improved
function generateETA(status) {
    switch (status) {
        case 'Partner Yet to Assign': return '30 minutes';
        case 'Partner Assigned': return '25 minutes';
        case 'Taken Order / On the way': return '15 minutes';
        case 'Delivered': return 'Delivered';
        default: return '30 minutes';
    }
}

// Simulate order status changes over time
document.addEventListener('DOMContentLoaded', () => {
    // Initial order times (simulated)
    const order1Time = new Date(new Date().getTime() - 2 * 60 * 1000); // 2 minutes ago
    const order2Time = new Date(new Date().getTime() - 8 * 60 * 1000); // 8 minutes ago

    updateOrderStatus('order12345', order1Time);
    updateOrderStatus('order12346', order2Time);


    // Update status every minute
    setInterval(() => {
        updateOrderStatus('order12345', order1Time);
        updateOrderStatus('order12346', order2Time);
    }, 60000);
});
