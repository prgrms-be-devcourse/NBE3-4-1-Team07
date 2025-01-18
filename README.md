# NBE3-4-1-team7 # 




### [요구사항](./img/요구사항.pdf) ###

### [ERD 설계](./img/ERD.png) ###

### [API 명세](./img/API.PNG) ###

### 브랜치 전략 ###

---
main : 언제든 실행 가능

dev : 개발 중인 기능 통합

* 새로운 브랜치 생성 예시
```
ex)
feature/#이슈번호
feature/#12
```
feature -> dev -> main

### 커밋 전략 ###

---

| 커밋타입     | 설명                                        |
|----------|-------------------------------------------|
| feat     | 기능 추가                                     |
| docs     | 문서 추가, 수정                                 |
| modify   | 기존 파일 수정                                  |
| fix      | 버그 수정                                     |
| refactor | 코드 스타일 변경 (코드 포매팅, 세미콜론 누락 등) 기능 수정이 없는 경우 |
| design   | 사용자 UI 디자인 변경 (CSS 등)                     |
| test     | 테스트 코드 추가                                 |
| rename   | 파일 혹은 폴더명을 수정만 한 경우                       |
| remove   | 파일을 삭제만 한 경우                              |



* 커밋 메시지 작성 예시
```
git commit -m "#이슈번호 feat - 상품 CRUD 구현"
git commit -m "#1 feat - 상품 CRUD 구현"
```




