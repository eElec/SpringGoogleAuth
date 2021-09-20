<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<meta name="google-signin-client_id" content="${CLIENT_ID}">
<script src="https://apis.google.com/js/platform.js" async defer></script>
</head>
<body>
	<div class="g-signin2" data-onsuccess="onSignIn"></div>

	<a href="#" onclick="signOut();">Sign out</a>

	<div id="data"></div>

	<script>
		function onSignIn(googleUser) {
			var profile = googleUser.getBasicProfile();
			console.log('id_token:', googleUser.getAuthResponse().id_token)
			fetch("validate", {
				method : "POST",
				headers : {
					'Content-Type' : 'application/json'
				},
				body : JSON.stringify({
					'idToken' : googleUser.getAuthResponse().id_token
				})
			})
			.then(resp=>resp.json())
			.then(data=>document.querySelector("#data").innerHTML = JSON.stringify(data))
		}
		function signOut() {
			var auth2 = gapi.auth2.getAuthInstance();
			auth2.signOut().then(function() {
				console.log('User signed out.');
			});
		}
	</script>
</body>
</html>