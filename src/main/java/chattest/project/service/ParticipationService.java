package chattest.project.service;



import chattest.project.model.Member;
import chattest.project.model.Participation;
import chattest.project.model.Reservation;
import chattest.project.repo.ParticipationRepository;
import chattest.project.repo.ReservationRepository;
import chattest.project.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;


    public Participation checkRoom(Long roomId, Long userId){

        Optional<Reservation> reservation = reservationRepository.findById(roomId);
        Optional<Member> user = userRepository.findById(userId);


        return participationRepository.findByReservationAndMember(reservation.get(),user.get());

    }
}
