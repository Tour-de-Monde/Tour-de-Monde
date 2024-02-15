## 소개 📝

### 💡 내 여행지로 🗺 만든 지도! 뚜르드몽드!

뚜르드몽드는 “지도 기반 참여형 공유 커뮤니티”로서 당신의 여행경험을 📍다양한 지도로 만들 수 있게 도와드리고 있어요!😉🌈

## 팀원 👨‍👨‍👧‍👧👩‍👦‍👦

- 이성규(팀장, 회의 주도, 요구사항 명세서 작성, DB 설계, 게시판 개발)
- 김동하(프레젠테이션, 발표자료 작성, 요구사항 명세서 작성, DB 설계, 지도 레퍼런스)
- 손경이(서기, 회의록 작성, 요구사항 명세서 작성, DB 설계, 에약/결제 레퍼런스)
- 심규대(요구사항 명세서 작성, DB 설계, 회원관리 레퍼런스)
- 이예원(ERD 다이어그램 작성, 요구사항 명세서 작성, DB 설계, 회원관리 레퍼런스)
- 전희영(프로젝트 문서 관리, 요구사항 명세서 작성, DB 설계, 예약/결제 레퍼런스)

## 팀 문화 🏠

1. 13시 ~ 18시는 필수 프로젝트 참여 시간!!
2. 13시 ~ 13시 30분 - 개인 별 일일 TODO_LIST 공유
3. 15시 ~ 15시 30분 - 중간 사항 채팅으로 말해주기
4. 13시 ~ 17시 - 개발 몰두 시간 (코어타임)
5. 17시~ 18시 - 각자 목표 달성 현황과 이슈 사항, 공지하기
6. 50분 ~ 정각은 쉬는 시간! 쉬는 시간 외에는 10분 안에 DM응답!
7. 팀 노션에 공지사항 페이지를 만들고 공유할 내용은 그 곳에 작성
8. 테킷수업시간(9시 ~ 18시) 외 작업을 한다면 팀 채널에 참여해서 화면공유를 권장(마이크는 off 해도 무방) - 10팀 채팅에 어떤 작업하는지 알려주기
9. 도메인 별로 작업하더라도 문제사항 또는 의견차이 발생시 모두에게 공유
10. 모르는 거 있으면 물어보고 대답해주기 몰라도 같이 고민해주기


## 📝 주요기능
1. 


## 💻 기술스택


## ✨ 페이지 이미지


## 📇 ERD
<img src="https://i.imgur.com/Jlj2DvV.png" width="600">

## 💣 트러블 슈팅
<details>
<summary>✔️ 배포한 서버에서 소셜 로그인 REST API 키가 맞지 않아 KOE101 에러 발생 - 손경이</summary>
<div>

<br/>

<img src="https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fc69962b0-3951-485b-b10a-5bb29576bba8%2Fc11bb9f3-f92b-48f0-b349-5d8adab713dd%2FUntitled.png?table=block&id=3839353a-ff97-4beb-a303-28f7f0788741&spaceId=c69962b0-3951-485b-b10a-5bb29576bba8&width=2000&userId=253c7ac7-ea76-405c-a201-0833bde7deeb&cache=v2" width="400">

<br/>

<h4>💭 첫 번째 시도</h4>

- application-prod.yml에 카카오 관련 정보 넣어봄 → 실패

<h4>💭 두 번째 시도</h4>

- application-prod.yml에 temp 디렉토리가 applicaton.yml과 달라서 똑같이 맞춰주기 → 실패
- <img src="https://www.notion.so/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fc69962b0-3951-485b-b10a-5bb29576bba8%2F779e0986-792f-46fb-8a80-f2d374903f0d%2FUntitled.png?table=block&id=3036a040-0b2d-484b-bd3c-49638c7a52d3&spaceId=c69962b0-3951-485b-b10a-5bb29576bba8&width=770&userId=253c7ac7-ea76-405c-a201-0833bde7deeb&cache=v2" width="200">

<h4>💭 세 번째 시도</h4>

- deploy.yml 코드 변경 - APPLICATION_SECRET_YML에 $ 추가 → 실패
- run: echo "$APPLICATION_SECRET_YML" > src/main/resources/application-secret.yml

<h4>💭 네 번째 시도</h4>

- 프로젝트 빌드할 때 테스트 코드 빼고 빌드 했었다.
- Dockerfile 코드 변경 - 소스 코드 복사에서 테스트 추가 → 실패
- COPY src/main src/main => COPY src src

<h4>💭 다섯 번째 시도</h4>

- 프로젝트 빌드할 때 테스트 코드 빼고 빌드 했었다.

<hr>

<h3>**💡 해결**</h3>

- 에러가 난 이유
  - 카카오 로그인하는 url을 보면 client_id가 ON_SECRET으로 application-secret.yml에 있는 client_id를 받아오지 못해서 생긴 에러이다.
- 새롭게 알게 된 것
  - application.yml에 카카오 설정이 있다면 application-prod.yml에는 없어도 된다.
  - application-secret.yml을 GitHub Actions 시크릿 환경변수로 만들 때는 주석은 없애고 값만 넣어야 한다.

</div>
</details>

<hr>

## 👨‍👨‍👧‍👧 구성원 소개
<hr>

<table>
  <tbody>
    <tr>
      <td align="center"><a href=""><img src="https://avatars.githubusercontent.com/u/79827000?v=4" width="100px;" alt="https://github.com/s2hmuel"/><br /><sub><b>BE 팀장 : 이성규 </b></sub></a><br /></td>
      <td align="center"><a href=""><img src="https://avatars.githubusercontent.com/u/79620776?v=4" width="100px;" alt="https://github.com/Dongha922"/><br /><sub><b>BE 팀원 : 김동하 </b></sub></a><br /></td>
      <td align="center"><a href=""><img src="https://avatars.githubusercontent.com/u/78200199?v=4" width="100px;" alt="https://github.com/Son-Gyeongi"/><br /><sub><b>BE 팀원 : 손경이 </b></sub></a><br /></td>
      <td align="center"><a href=""><img src="https://avatars.githubusercontent.com/u/102494128?v=4" width="100px;" alt="https://github.com/kds98"/><br /><sub><b>BE 팀원 : 심규대 </b></sub></a><br /></td>
      <td align="center"><a href=""><img src="https://avatars.githubusercontent.com/u/99163851?v=4" width="100px;" alt="https://github.com/AidennnLee"/><br /><sub><b>BE 팀원 : 이예원 </b></sub></a><br /></td>
      <td align="center"><a href=""><img src="https://avatars.githubusercontent.com/u/129508219?v=4" width="100px;" alt="https://github.com/geniushee"/><br /><sub><b>BE 팀원 : 전희영 </b></sub></a><br /></td>
    </tr>
  </tbody>
</table>