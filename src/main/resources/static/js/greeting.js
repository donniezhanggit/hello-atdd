function getGreeting () {
  const url = `${window.location.origin}/greeting`;
  const greetingContent = document.querySelector("#greeting-content");

  fetch(url)
    .then((res) => res.json())
    .then((greeting) => greetingContent.innerHTML = greeting.message)
    .catch((error) => greetingContent.innerHTML = error)
    .then(() => greetingContent.hidden = false);
}

document.addEventListener("DOMContentLoaded", () => {
  const askGreeting = document.querySelector("#ask-greeting");

  askGreeting.addEventListener("click", () => getGreeting());
});
