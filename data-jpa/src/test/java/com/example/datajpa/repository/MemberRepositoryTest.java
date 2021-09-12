package com.example.datajpa.repository;

import com.example.datajpa.dto.MemberDto;
import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        // List 조회 검증
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        // Count 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // Delete 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("AAA", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testQuery() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> members = memberRepository.findUser("AAA", 10);
        assertThat(members.get(0).getUsername()).isEqualTo("AAA");
        assertThat(members.get(0).getAge()).isEqualTo(10);
    }

    @Test
    public void findUsernameList() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> memberNames = memberRepository.findUsernameList();
        for (String name : memberNames) {
            System.out.println("Username : " + name);
        }
    }

    @Test
    public void findMemberDto(){
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member member = new Member("AAA", 10);
        member.setTeam(team);
        memberRepository.save(member);

        List<MemberDto> memberDto = memberRepository.findMemberByDto();
        for(MemberDto dto : memberDto){
            System.out.println("dto : " + dto);
        }
    }

    @Test
    public void findByNames(){
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        for (Member member : result) {
            System.out.println("Member : " + member);
        }
    }

    @Test
    public void returnType(){
        Member member1 = new Member("AAA", 10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> members = memberRepository.findListByUsername("AAA");
        Member member = memberRepository.findMemberByUsername("AAA");
        Optional<Member> optional = memberRepository.findOptionalByUsername("AAA");

        assertThat(members).isInstanceOf(List.class);
        assertThat(member).isInstanceOf(Member.class);
        assertThat(optional).isInstanceOf(Optional.class);
    }

    @Test
    public void paging(){
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest
                .of(0, 3, Sort.by(Sort.Direction.DESC, "username"));// Page Index가 0부터 시작한다.

        // when
        Page<Member> page = memberRepository.findPageByAge(10, pageRequest);
        Page<MemberDto> toMap
                = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));// DTO로 변환

        // then
        List<Member> content = page.getContent();
        long totalElement = page.getTotalElements();

        for (Member member : content) {
            System.out.println("member : " + member);
        }
        System.out.println("totalElements : " + totalElement);

        assertThat(content.size()).isEqualTo(3);            // paging 된 element 개수를 가져온다.
        assertThat(page.getTotalElements()).isEqualTo(5);   // 전체 element 개수를 가져온다.
        assertThat(page.getNumber()).isEqualTo(0);          // paging 시작 index를 가져온다.
        assertThat(page.getTotalPages()).isEqualTo(2);      // page 개수를 가져온다.
        assertThat(page.isFirst()).isTrue();                // 첫번째 page인지 확인
        assertThat(page.hasNext()).isTrue();                // 다음 page가 있는지 확인


        Slice<Member> slice = memberRepository.findSliceByAge(10, pageRequest);

        assertThat(content.size()).isEqualTo(3);            // paging 된 element 개수를 가져온다.
        assertThat(slice.getNumber()).isEqualTo(0);          // paging 시작 index를 가져온다.
        assertThat(slice.isFirst()).isTrue();                // 첫번째 page인지 확인
        assertThat(slice.hasNext()).isTrue();                // 다음 page가 있는지 확인

        List<Member> list = memberRepository.findListByAge(10, pageRequest);

        Page<Member> pageDivideCount = memberRepository.findPageDivideCountByAge(10, pageRequest);

        List<Member> contentDivideCount = page.getContent();
        long totalElementDivideCount = page.getTotalElements();

        System.out.println("totalElementDivideCount : " + totalElementDivideCount);

        assertThat(content.size()).isEqualTo(3);            // paging 된 element 개수를 가져온다.
        assertThat(pageDivideCount.getTotalElements()).isEqualTo(5);   // 전체 element 개수를 가져온다.
        assertThat(pageDivideCount.getNumber()).isEqualTo(0);          // paging 시작 index를 가져온다.
        assertThat(pageDivideCount.getTotalPages()).isEqualTo(2);      // page 개수를 가져온다.
        assertThat(pageDivideCount.isFirst()).isTrue();                // 첫번째 page인지 확인
        assertThat(pageDivideCount.hasNext()).isTrue();                // 다음 page가 있는지 확인
    }


    @Test
    public void bulkUpdate(){
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));

        int resultCount = memberRepository.bulkAgePlus(20);
        // 벌크성 쿼리를 실행한 후 영속성 컨텍스트를 날려준다.
        entityManager.flush();
        entityManager.clear();


        assertThat(resultCount).isEqualTo(3);
    }

    @Test
    public void findMemberLazy(){
        // given
        // member1 -> teamA
        // member2 -> teamB

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        entityManager.flush();
        entityManager.clear();

        // when
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.getClass = " + member.getTeam().getClass());
            System.out.println("member.getTeam = " + member.getTeam()); // Team 데이터를 가져올때 쿼리문이 나가게 된다.
        }
    }

    @Test
    public void queryHint(){
        // given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        entityManager.flush();
        entityManager.clear();

        // Read Only의 경우 변경 감지 체크를 안하기 때문에 update 쿼리문이 날라가지 않는다.
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");
        entityManager.flush();
    }

    @Test
    public void lock(){
        // given
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        entityManager.flush();
        entityManager.clear();

        List<Member> result = memberRepository.findLockByUsername("member1");
    }

    @Test
    public void callCustom(){
        List<Member> result = memberRepository.findMemberCustom();
    }

    @Test
    public void JpaEventBaseEntity() throws InterruptedException {
        // given
        Member member1 = new Member("member1");
        memberRepository.save(member1);

        Thread.sleep(100);
        member1.setUsername("member2");

        entityManager.flush();
        entityManager.clear();

        // when
        Member findMember = memberRepository.findById(member1.getId()).get();

        // then
        System.out.println("findMember.getCreatedDate() = " + findMember.getCreatedDate());
        System.out.println("findMember.getLastModifiedDate() = " + findMember.getLastModifiedDate());
        System.out.println("findMember.getCreatedBy() = " + findMember.getCreatedBy());
        System.out.println("findMember.getLastModifiedBy() = " + findMember.getLastModifiedBy());
    }
}