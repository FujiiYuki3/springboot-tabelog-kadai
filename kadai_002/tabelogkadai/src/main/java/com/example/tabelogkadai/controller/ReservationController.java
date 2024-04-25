package com.example.tabelogkadai.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tabelogkadai.entity.Reservation;
import com.example.tabelogkadai.entity.Shop;
import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.form.ReservationEditForm;
import com.example.tabelogkadai.form.ReservationInputForm;
import com.example.tabelogkadai.form.ReservationRegisterForm;
import com.example.tabelogkadai.repository.ReservationRepository;
import com.example.tabelogkadai.repository.ShopRepository;
import com.example.tabelogkadai.security.UserDetailsImpl;
import com.example.tabelogkadai.service.ReservationService;

@Controller
@RequestMapping("/login")
public class ReservationController {
	private final ReservationRepository reservationRepository;
	private final ShopRepository shopRepository;
	private final ReservationService reservationService;
	
	public ReservationController(ReservationRepository reservationRepository, ShopRepository shopRepository, ReservationService reservationService) {
		this.reservationRepository = reservationRepository;
		this.shopRepository = shopRepository;
		this.reservationService = reservationService;
	}
	
	//予約一覧ページ
	@GetMapping("/reservations")
	public String reservations(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC)Pageable pageable, Model model) {
		User user = userDetailsImpl.getUser();
		
		//予約日時が若い順で並べる
		Page<Reservation> reservationPage = reservationRepository.findByUserOrderByBookingDateAscBookingTimeAsc(user, pageable);
		
		model.addAttribute("reservationPage", reservationPage);
		
		return "paidUser/reservations/index";
	}
	
	//予約詳細ページ
	@GetMapping("/reservations/{id}")
	public String show(@PathVariable(name = "id")Integer id, Model model) {
		Reservation reservation = reservationRepository.getReferenceById(id);
		ReservationEditForm reservationEditForm = new ReservationEditForm(id, reservation.getUser(), reservation.getShop(), reservation.getBookingDate(), reservation.getBookingTime(), reservation.getNumberOfPeople());
		
		model.addAttribute("reservation", reservation);
		model.addAttribute("reservationEditForm", reservationEditForm);
		
		return "paidUser/reservations/show";
	}
	
	//予約登録ページ
	@GetMapping("/{id}/reservations/input")
	public String input(@PathVariable(name = "id")Integer id,@ModelAttribute @Validated ReservationInputForm reservationInputForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		Shop shop = shopRepository.getReferenceById(id);
		Integer numberOfPeople = reservationInputForm.getNumberOfPeople();
		Integer seats = shop.getSeats();
		
		if(numberOfPeople != null) {
			if(!reservationService.isWithinCapacity(numberOfPeople, seats)) {
				FieldError fieldError = new FieldError(bindingResult.getObjectName(), "numberOfPeople", "空席がありません。");
				bindingResult.addError(fieldError);
			}
		}
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("shop", shop);
			model.addAttribute("errorMessage", "予約内容に不備があります。");
			return "show";
		}
		
		redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);
		
		return "redirect:/login/{id}/reservations/confirm";
	}
	
	//予約確認ページ
	@GetMapping("/{id}/reservations/confirm")
	public String confirm(@PathVariable(name = "id")Integer id,@ModelAttribute ReservationInputForm reservationInputForm, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		Shop shop = shopRepository.getReferenceById(id);
		User user = userDetailsImpl.getUser();
		
		LocalDate bookingDate = reservationInputForm.getBookingDate();
		LocalTime bookingTime = reservationInputForm.getBookingTime();
		Integer numberOfPeople = reservationInputForm.getNumberOfPeople();
		
		ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(user.getId(), shop.getId(), bookingDate, bookingTime, numberOfPeople);
		
		model.addAttribute("shop", shop);
		model.addAttribute("reservationRegisterForm", reservationRegisterForm);
		
		return "paidUser/reservations/confirm";
	}
	
	//予約登録処理
	@PostMapping("/{id}/reservations/create")
	public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm, RedirectAttributes redirectAttributes) {
		reservationService.create(reservationRegisterForm);
		
		redirectAttributes.addFlashAttribute("createSuccessMessage", "予約完了しました。");
		
		return "redirect:/login/reservations?reserved";
	}
	
	//予約キャンセル処理
	@PostMapping("/reservations/{id}/delete")
	public String delete(@PathVariable(name = "id")Integer id, RedirectAttributes redirectAttributes) {
		reservationRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("deleteSuccessMessage", "予約をキャンセルしました。");
		
		return "redirect:/login/reservations";
	}
	
	//予約変更処理
	@PostMapping("/reservations/{id}/update")
	public String update(@ModelAttribute @Validated ReservationEditForm reservationEditForm, RedirectAttributes redirectAttributes) {
		reservationService.update(reservationEditForm);
		
		redirectAttributes.addFlashAttribute("updateSuccessMessage", "予約を変更しました。");
		
		return "redirect:/login/reservations";
	}

}
