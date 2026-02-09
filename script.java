const pillars = ["SEO", "OIS", "SSA", "VAULT", "NETWORK", "WEALTH", "LEGAL", "ETHICS", "QUANT", "GENETIC", "AERO", "CYBER", "BIO", "ENERGY", "SPACE", "INTEL", "MINING", "TRADE", "NEURAL", "CORE", "SENSORS", "LOGIC", "MEMORY", "FLUID", "OS", "GRID", "FLEET", "PATENT", "MEDIA", "POLITIC", "CULTURE", "TIME", "ETERNITY"];

const term = document.getElementById('terminal');
const container = document.getElementById('pillarContainer');
let API_KEY = localStorage.getItem('GEMINI_KEY') || "";

// Generate Pillars
pillars.forEach(p => {
    const div = document.createElement('div');
    div.className = 'pillar-box';
    div.id = `pillar-${p}`;
    div.innerText = p;
    container.appendChild(div);
});

if (API_KEY) {
    document.getElementById('status').innerText = "ENCRYPTION: ACTIVE";
    document.getElementById('status').className = "text-[10px] border border-green-500 px-2 py-1 text-green-500";
    log("Auth Key Loaded from Secure Storage.");
}

function log(msg, type = 'SYSTEM') {
    const div = document.createElement('div');
    div.innerHTML = `<span class="opacity-50">[${type}]</span> ${msg}`;
    term.appendChild(div);
    term.scrollTop = term.scrollHeight;
}

async function execute() {
    const input = document.getElementById('cmd');
    const val = input.value.trim();
    if (!val) return;

    if (val.startsWith('auth ')) {
        const key = val.split(' ')[1];
        localStorage.setItem('GEMINI_KEY', key);
        API_KEY = key;
        log("New Auth Key Registered. System Rebooting...", "SECURITY");
        location.reload();
        return;
    }

    if (!API_KEY) {
        log("ACCESS DENIED. PLEASE PROVIDE AUTH KEY.", "ERROR");
        input.value = '';
        return;
    }

    log(val, "OWNER");
    input.value = '';
    
    // The "Genesis" sequence
    if (val.toLowerCase() === 'genesis') {
        let i = 0;
        const interval = setInterval(() => {
            if (i < pillars.length) {
                document.getElementById(`pillar-${pillars[i]}`).classList.add('pillar-active');
                log(`Activating ${pillars[i]}...`);
                i++;
            } else {
                clearInterval(interval);
                log("ALL 33 PILLARS ONLINE. OS IS SOVEREIGN.", "GENESIS");
            }
        }, 80);
    } else {
        // Here we connect to Gemini
        log("Transmitting to Singularity Core...");
        try {
            const response = await fetch(`https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=${API_KEY}`, {
                method: "POST",
                body: JSON.stringify({ contents: [{ parts: [{ text: val }] }] })
            });
            const data = await response.json();
            const aiText = data.candidates[0].content.parts[0].text;
            log(aiText, "AI");
        } catch (e) {
            log("Transmission Failed. Check API Key.", "ERROR");
        }
    }
}
