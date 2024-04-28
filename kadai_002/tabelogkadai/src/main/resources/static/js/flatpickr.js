let maxDate = new Date();
maxDate = maxDate.setMonth(maxDate.getMonth() + 3);

let minDate = new Date();
minDate = minDate.setDate(minDate.getDate() + 1);

flatpickr('#bookingDateTime', {
	enableTime: true,
	locale: 'ja',
	minDate: minDate,
	maxDate: maxDate
});