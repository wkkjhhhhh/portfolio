 $(document).ready(function() {
        // 체크박스 클릭 시 총 가격 업데이트
        $('input[type="checkbox"]').click(function() {
            updateTotalAmount();
        });

        // 수량 변경 시 총 가격 업데이트
        $('input[type="number"]').change(function() {
            updateTotalAmount();
        });
    });
    //총 금액
    const updateTotalAmount = () => {
        var totalAmount = 0;
        $('input[type="checkbox"]:checked').each(function() {
            var idx = $(this).val();
            var priceStr = $('#totalPrice_' + idx).text().replace('원', '').replace(',', '');
            var price = parseInt(priceStr);
            totalAmount += price;
        });

        $('#totalAmount').text(totalAmount.toLocaleString('ko-KR') + '원');

        $('#totalPay').val(totalAmount.toLocaleString('ko-KR') + '원');

    };
        //아이템 수량 하나씩 빼기
        const quantityDown = (idx) => {
        var quantityInput = document.getElementById('quantity_' + idx);
        var totalPriceTd = document.getElementById('totalPrice_' + idx);
        var quantity = parseInt(quantityInput.value);
        var price = parseInt(document.getElementById('price_' + idx).value);
        var totalPrice = price * quantity;
        if (quantity > 1) {
            quantity--;
            totalPrice = price * quantity;
            updatePrice(totalPrice, totalPriceTd);
            updateTotalAmount();
        }
        quantityInput.value = quantity;
        $.ajax({
            url: '/updateCart',
            data: {idx: idx,quantity:quantity},
            dataType: 'json',
            type: 'POST',
            success: function(data) {
                // 요청이 성공했을 때 실행할 코드
                console.log('성공');
            },
            error: function() {
                // 요청이 실패했을 때 실행할 코드
                console.log('Failed to load page');
            }
        });

    }
    //아이템 수량 하나씩 더하기
    const quantityUp = (idx) => {
        var quantityInput = document.getElementById('quantity_' + idx);
        var totalPriceTd = document.getElementById('totalPrice_' + idx);
        var quantity = parseInt(quantityInput.value);
        var price = parseInt(document.getElementById('price_' + idx).value);
        var totalPrice = price * quantity;

        quantity++;
        totalPrice = price * quantity;
        updatePrice(totalPrice, totalPriceTd);
        updateTotalAmount();
        quantityInput.value = quantity;
        $.ajax({
            url: '/updateCart',
            data: {idx: idx,quantity:quantity},
            dataType: 'json',
            type: 'POST',
            success: function(data) {
                // 요청이 성공했을 때 실행할 코드
                console.log('성공');
            },
            error: function() {
                // 요청이 실패했을 때 실행할 코드
                console.log('Failed to load page');
            }
        });
    }
    const updatePrice = (totalPrice, totalPriceTd) => {
        var total = totalPrice.toLocaleString('ko-KR');
        totalPriceTd.innerText = total + '원';

    }
