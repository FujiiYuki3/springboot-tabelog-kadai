const stripe = Stripe('pk_test_51OlScLC7ku4vkHH6FxNqQVprxtBvDXSEuBgscp0sZmKkgglXHkouAtnXNjBWXLIJTQnQSsbCPRNXxQXk7DdFcrdi00RA7fKsL2');
const paymentButton = document.querySelector('#paymentButton');
const updateButton = document.querySelector('#updateButton');

if (paymentButton) {
	paymentButton.addEventListener('click', () => {
		stripe.redirectToCheckout({
			sessionId: sessionId
		})
	});
}

if (updateButton) {
	updateButton.addEventListener('click', () =>  {
		if(portalSessionUrl  && portalSessionUrl !== "portalSessionUrl"){
			window.location.href = portalSessionUrl; // カスタマーポータルのURLにリダイレクト
		}else{
			console.log('カスタマーポータルセッションのURLが見つかりません');
		}
	});
}