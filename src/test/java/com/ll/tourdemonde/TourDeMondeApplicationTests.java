package com.ll.tourdemonde;

import com.ll.tourdemonde.member.entity.Member;
import com.ll.tourdemonde.member.repository.MemberRepository;
import com.ll.tourdemonde.place.entity.Place;
import com.ll.tourdemonde.place.repository.PlaceRepository;
import com.ll.tourdemonde.post.entity.Post;
import com.ll.tourdemonde.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "classpath:application-test.yml")
class TourDeMondeApplicationTests {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PlaceRepository placeRepository;

	@Test
	public void 게시물작성테스트() {

		Member member1 = memberRepository.save(
				Member.builder()
						.username("user1")
						.password("1234")
						.build()
		);

		Member member2 = memberRepository.save(
				Member.builder()
						.username("user1")
						.password("1234")
						.build()
		);


		Place place1 = placeRepository.save(
				Place.builder()
						.name("장소1")
						.coordinate("1.1.1.1")
						.build()
		);

		Place place2 = placeRepository.save(
				Place.builder()
						.name("장소2")
						.coordinate("1.1.1.2")
						.build()
		);

		Post post1 = postRepository.save(
				Post.builder()
						.title("게시글1")
						.author(member1)
						.build()
		);

		post1.addPlace(place2);
		post1.addPlace(place1);

		Post post2 = postRepository.save(
				Post.builder()
						.title("게시글2")
						.author(member2)
						.build()
		);

		post2.addPlace(place1);
	}

}




