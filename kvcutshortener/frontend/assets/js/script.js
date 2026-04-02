const input = document.querySelector('#url-input');
const form = document.querySelector("#url-form");
const result = document.querySelector("#result");

document.addEventListener("click", e => {
    const btn = e.target.closest('#copy');
    if (btn){
        const link = document.querySelector('#link');
        navigator.clipboard.writeText(link.textContent);
    }
} )

form.addEventListener('submit', e => { 
    e.preventDefault()
    createShortUrl(input.value);
})

async function createShortUrl(url){
    try {
        const response = await fetch ("http://localhost:8080/api/urls", {
            method: "POST",
            headers: { "Content-Type": "application/json"},
            body: JSON.stringify({url})
        });

        const data = await response.json();
        console.log(data);
        updateResult(data);
    } catch (err) {
        console.error(err);
    }
}

function updateResult( { urlMontada } ) {

    result.innerHTML = `<a id="link" href="">${urlMontada}</a><button id="copy"><svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="4" stroke-linecap="square" stroke-linejoin="round" class="lucide lucide-copy-icon lucide-copy"><rect width="14" height="14" x="8" y="8" rx="2" ry="2"/><path d="M4 16c-1.1 0-2-.9-2-2V4c0-1.1.9-2 2-2h10c1.1 0 2 .9 2 2"/></svg></button>`;
    result.classList.remove('hiddenresult')
    result.classList.add('visibleresult');
}