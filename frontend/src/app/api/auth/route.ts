// import NextAuth from "next-auth";
// import CredentialsProvider from "next-auth/providers/credentials";
//
// export default NextAuth({
//     providers: [
//         CredentialsProvider({
//             name: "Credentials",
//             credentials: {
//                 username: { label: "Username", type: "text" },
//                 password: { label: "Password", type: "password" },
//             },
//             authorize: async (credentials) => {
//                 const res = await fetch("http://localhost:8080/admin/login", {
//                     method: "POST",
//                     headers: { "Content-Type": "application/json" },
//                     body: JSON.stringify(credentials),
//                 });
//
//                 if (res.ok) {
//                     const user = await res.json();
//                     return user ? { ...user } : null;
//                 }
//                 return null;
//             },
//         }),
//     ],
//     session: {
//         strategy: "jwt",
//     },
//     callbacks: {
//         async jwt({ token, user }) {
//             if (user) {
//                 token.id = user.id;
//             }
//             return token;
//         },
//         async session({ session, token }) {
//             session.user.id = token.id;
//             return session;
//         },
//     },
// });
