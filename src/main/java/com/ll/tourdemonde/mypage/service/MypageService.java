package com.ll.tourdemonde.mypage.service;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.repository.MemberRepository;
<<<<<<< HEAD
import com.ll.tourdemonde.payment.order.entity.Order;
import com.ll.tourdemonde.payment.order.repository.OrderRepository;
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.repository.PostRepository;
import com.ll.tourdemonde.reservation.entity.Reservation;
import com.ll.tourdemonde.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
=======
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
>>>>>>> 380496b (feat : 마이페이지 내가 쓴 글 목록 구현)
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
<<<<<<< HEAD
    private final OrderRepository orderRepository;

    //현재 로그인한 사용자가 작성한 글 리스트 반환
<<<<<<< HEAD
=======

>>>>>>> 380496b (feat : 마이페이지 내가 쓴 글 목록 구현)
=======
>>>>>>> 26a5936 (feat : 마이페이지 좋아요 누른 글 리스트 구현)
    public List<Post> myPostList(String username) {
        List<Post> postList = postRepository.findAll();
        Optional<Member> member = memberRepository.findByUsername(username);
        Long id = member.get().getId();

        List<Post> myPostList = postList.stream()
                .filter(post -> post.getAuthor().getId().equals(id))
                .collect(Collectors.toList());

        return myPostList;
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 26a5936 (feat : 마이페이지 좋아요 누른 글 리스트 구현)

    //현재 로그인한 사용자가 좋아요 한 글 리스트 반환
    public List<Post> votePostList(String username) {
        List<Post> postList = postRepository.findAll();
        Optional<Member> member = memberRepository.findByUsername(username);

<<<<<<< HEAD
=======

>>>>>>> 26a5936 (feat : 마이페이지 좋아요 누른 글 리스트 구현)
        List<Post> votePostList = postList.stream()
                .filter(post -> post.getVoter().stream().anyMatch(v -> v.equals(member.get())))
                .collect(Collectors.toList());

        return votePostList;
    }
<<<<<<< HEAD

    //현재 로그인한 사용자의 예약 리스트 반환
    public List<Order> myOrderList(String username) {
        List<Order> orderList = orderRepository.findAll();
        Optional<Member> member = memberRepository.findByUsername(username);
        Long id = member.get().getId();

        List<Order> myOrderList = orderList.stream()
                .filter(order -> order.getBuyer().getId().equals(id))
                .collect(Collectors.toList());

        return myOrderList;
    }
=======
>>>>>>> 380496b (feat : 마이페이지 내가 쓴 글 목록 구현)
=======
>>>>>>> 26a5936 (feat : 마이페이지 좋아요 누른 글 리스트 구현)
}
