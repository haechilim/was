function validateId(id) {
    const xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function (event) {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            const json = JSON.parse(xhr.responseText);
            updateError(json.code === "OK");
        }
    };

    xhr.open("GET", "/api/unique?id=" + id);
    xhr.send();
}

function updateError(ok) {
    document.querySelector("p.id span.error").innerHTML = ok ? "사용가능" : "중복된 아이디";
}

document.addEventListener("DOMContentLoaded", function() {
    document.querySelector("#id").addEventListener("keyup", function(event) {
        validateId(event.target.value);
    });
});