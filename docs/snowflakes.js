const snowflakeCharacters = ['❄', '✻', '✼', '✽', 'λ'];

const createSnowflake = () => {
    const snowflake = document.createElement('div');
    snowflake.classList.add('snowflake');
    snowflake.textContent = snowflakeCharacters[Math.floor(Math.random() * snowflakeCharacters.length)];
    snowflake.style.left = `${Math.random() * 100}vw`;
    snowflake.style.fontSize = `${Math.random() * 20 + 10}px`;
    snowflake.style.animationDuration = `${Math.random() * 3 + 2}s`;
    document.body.appendChild(snowflake);

    setTimeout(() => snowflake.remove(), 5000);
};

setInterval(createSnowflake, 200);

/* Snowflake styling in the JS file */
const style = document.createElement('style');
style.innerHTML = `
    .snowflake {
        position: absolute;
        top: 0;
        color: white;
        font-size: 10px;
        pointer-events: none;
        animation: fall linear infinite;
    }

    @keyframes fall {
        0% {
            transform: translateY(-100px);
            opacity: 1;
        }
        100% {
            transform: translateY(100vh);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);