# NBE3-4-1-team7 # 

---
## 목차 ##

---



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

| 커밋타입 | 설명        |
|------|-----------|
| feat | 기능 추가     |
| docs | 문서 추가, 수정 |
| fix  | 버그 수정     |
| refactor | 코드 리팩토링   |
| style | 코드 포맷팅    |
| test | 테스트 코드 추가 |

* 커밋 메시지 작성 예시
```
git commit -m "#이슈번호 feat - 상품 CRUD 구현"
git commit -m "#1 feat - 상품 CRUD 구현"
```




