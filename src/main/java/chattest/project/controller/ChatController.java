package chattest.project.controller;

import chattest.project.dto.ChatMessageSaveDto;

import chattest.project.model.Member;
import chattest.project.model.Reservation;
import chattest.project.pubsub.RedisPublisher;
import chattest.project.repo.*;
import chattest.project.service.ChatRedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRedisCacheService chatRedisCacheService;
    private final ChannelTopic channelTopic;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ParticipationRepository participationRepository;

    // TODO: 2023/07/16 시큐리티 설정 추가
    //private final HeaderTokenExtractor headerTokenExtractor;
    //private final JwtDecoder jwtDecoder;

    @MessageMapping("/chat/message")
    public void message(ChatMessageSaveDto message, @Header("userId") Long userId){

        //@Header("info") String token

        Optional<Member> user = userRepository.findById(userId);
        Member member = user.get();

        Reservation reservation = reservationRepository.findById(Long.valueOf(message.getRoomId())).orElseThrow(RuntimeException::new);

        if(!participationRepository.existsByReservationAndMember(reservation,member)){
            throw new RuntimeException("참여하지 않은 사람");
        }


        log.info(member.getName());


        //UserInfo userInfo = jwtDecoder.decodeUsername(headerTokenExtractor.extract(token));

        message.setNickname(member.getEmail());
        message.setWriter(member.getName());
        message.setType(ChatMessageSaveDto.MessageType.TALK);
        message.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")));

        redisPublisher.publish(channelTopic,message);
        chatRedisCacheService.addChat(message);
    }

}