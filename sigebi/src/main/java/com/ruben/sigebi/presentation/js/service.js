// =============================================
//  SIGEBI — Service (User) Portal JavaScript
// =============================================

import {
  getAllResources,
  requestLoan,
  getUserLoans,
  returnLoan,
  getUserPenalties,
  applyPenalty,
  removePenalty,
  searchResource
} from "./api.js";

/* ── MOCK DATA ── */
const RES = [
  { id:'a1b2', title:'Clean Code',                    author:'Robert C. Martin',    type:'BOOK',    lang:'Inglés',  copies:3, state:'available' },
  { id:'e5f6', title:'Domain-Driven Design',          author:'Eric Evans',          type:'BOOK',    lang:'Inglés',  copies:1, state:'available' },
  { id:'i9j0', title:'El Quijote',                    author:'Miguel de Cervantes', type:'BOOK',    lang:'Español', copies:0, state:'loaned'    },
  { id:'m3n4', title:'Introducción a los Algoritmos', author:'Cormen et al.',       type:'BOOK',    lang:'Español', copies:2, state:'available' },
  { id:'q7r8', title:'Revista ACMEI Vol.12',          author:'ACMEI',               type:'JOURNAL', lang:'Español', copies:1, state:'available' },
  { id:'u1v2', title:'Spring Boot en Acción',         author:'Craig Walls',         type:'BOOK',    lang:'Español', copies:4, state:'available' },
  { id:'y5z6', title:'Patrones de Diseño GoF',        author:'Gang of Four',        type:'BOOK',    lang:'Inglés',  copies:0, state:'loaned'    },
];

const LOANS = [
  { id:'l001', book:'Clean Code',           author:'Robert C. Martin', start:'2025-03-15', due:'2025-03-22', state:'OVERDUE', copyId:'c001' },
  { id:'l002', book:'Domain-Driven Design', author:'Eric Evans',       start:'2025-04-08', due:'2025-04-15', state:'ON_TIME', copyId:'c002' },
];

const PENS = [
  { id:'p001', book:'Clean Code', loan:'l001', start:'2025-03-23', end:'2025-03-30' },
];

let retId = null;

/* ── NAVIGATION ── */
export function sp(page, btn) {
  document.querySelectorAll('.pg').forEach(p => p.classList.remove('on'));
  document.getElementById('pg-' + page).classList.add('on');
  document.querySelectorAll('.nl').forEach(n => n.classList.remove('on'));
  document.querySelectorAll('.si').forEach(s => s.classList.remove('on'));
  if (btn) btn.classList.add('on');

  if (page === 'prestamos')      renderLoans();
  if (page === 'penalizaciones') renderPens();
}

/* ── RESOURCES ── */
function renderRes(list) {
  const tb = document.getElementById('res-tbody');
  if (!list.length) {
    tb.innerHTML = '<tr><td colspan="6"><div class="empty"><div class="empty-i">◻</div>Sin resultados</div></td></tr>';
    return;
  }
  tb.innerHTML = list.map(r => `
    <tr>
      <td><div class="t-title">${r.title}</div><div class="t-sub">${r.author}</div></td>
      <td>${r.type}</td>
      <td>${r.lang}</td>
      <td style="font-weight:500;color:${r.copies > 0 ? 'var(--green)' : 'var(--red)'}">${r.copies}</td>
      <td><span class="bdg ${r.copies > 0 ? 'av' : 'lo'}">${r.copies > 0 ? 'Disponible' : 'Prestado'}</span></td>
      <td>
        <button class="btn bp btn-sm" ${r.copies === 0 ? 'disabled' : ''}
          onclick="prefill('${r.id}','${r.title}',${r.copies})">Solicitar</button>
      </td>
    </tr>`).join('');
}

export function filterRes() {
  const q = document.getElementById('q-cat').value.toLowerCase();
  const t = document.getElementById('q-type').value;
  renderRes(
    RES.filter(r =>
      (!q || r.title.toLowerCase().includes(q) || r.author.toLowerCase().includes(q)) &&
      (!t || r.type === t)
    )
  );
}

/* ── LOANS ── */
function renderLoans() {
  const el = document.getElementById('loans-list');
  if (!LOANS.length) {
    el.innerHTML = '<div class="empty"><div class="empty-i">▤</div>Sin préstamos activos</div>';
    return;
  }
  el.innerHTML = LOANS.map(l => `
    <div class="lc">
      <div class="lc-icon">📖</div>
      <div class="lc-info">
        <div class="lc-book">${l.book}</div>
        <div class="lc-meta">${l.author}</div>
        <div class="lc-dates">Inicio: ${l.start} · Vence:
          <span style="color:${l.state === 'OVERDUE' ? 'var(--red)' : 'var(--text)'};font-weight:500">${l.due}</span>
        </div>
      </div>
      <div class="lc-actions">
        <span class="bdg ${l.state === 'OVERDUE' ? 'ov' : 'ok'}">${l.state === 'OVERDUE' ? 'Vencido' : 'Al día'}</span>
        <button class="btn bs btn-sm" onclick="openRet('${l.id}','${l.book}')">Devolver</button>
      </div>
    </div>`).join('');
}

/* ── PENALTIES ── */
function renderPens() {
  const el = document.getElementById('pen-list');
  if (!PENS.length) {
    el.innerHTML = '<div class="empty"><div class="empty-i">✓</div>Sin penalizaciones activas</div>';
    return;
  }
  el.innerHTML = PENS.map(p => `
    <div class="pcard">
      <div class="pcard-body">
        <div class="pcard-title">Devolución tardía — ${p.book}</div>
        <div class="pcard-meta">Desde ${p.start} hasta ${p.end} · ID: ${p.id}</div>
      </div>
      <span class="bdg ov">Activa</span>
    </div>`).join('');
}

/* ── LOAN REQUEST ── */
export function prefill(id, title, copies) {
  document.getElementById('sol-id').value    = id;
  document.getElementById('sol-title').value = title;
  document.getElementById('found-name').textContent   = title;
  document.getElementById('found-copies').textContent = copies;
  document.getElementById('found-chip').style.display = 'block';
  sp('solicitar');
}

export function searchSol() {
  const q    = document.getElementById('sol-title').value.toLowerCase();
  const f    = RES.find(r => r.title.toLowerCase().includes(q) && r.copies > 0 && q.length > 2);
  const chip = document.getElementById('found-chip');
  if (f) {
    document.getElementById('found-name').textContent   = f.title;
    document.getElementById('found-copies').textContent = f.copies;
    document.getElementById('sol-id').value             = f.id;
    chip.style.display = 'block';
  } else {
    chip.style.display = 'none';
  }
}

export function doLoan() {
  if (!document.getElementById('sol-id').value) {
    toast('Selecciona un recurso primero', 'e');
    return;
  }
  toast('Préstamo solicitado. Copia asignada exitosamente', 's');
  document.getElementById('sol-title').value          = '';
  document.getElementById('sol-id').value             = '';
  document.getElementById('found-chip').style.display = 'none';
  setTimeout(() => sp('prestamos'), 1000);
}

/* ── RETURN ── */
export function openRet(id, name) {
  retId = id;
  document.getElementById('ret-name').textContent = name;
  document.getElementById('ret-modal').classList.add('op');
}

export function closeMo(id) {
  document.getElementById(id).classList.remove('op');
}

export function doReturn() {
  closeMo('ret-modal');
  const i = LOANS.findIndex(l => l.id === retId);
  if (i !== -1) LOANS.splice(i, 1);
  toast('Devolución registrada. La copia está disponible', 's');
  renderLoans();
}

/* ── TOAST ── */
export function toast(msg, type = 'ok') {
  const a = document.getElementById('ta');
  const t = document.createElement('div');
  t.className  = 't ' + type;
  t.textContent = msg;
  a.appendChild(t);
  setTimeout(() => t.remove(), 3200);
}

/* ── INIT & GLOBAL EXPOSURE ── */
renderRes(RES);

// Expose to global scope (required for inline onclick handlers with type="module")
window.sp         = sp;
window.filterRes  = filterRes;
window.prefill    = prefill;
window.searchSol  = searchSol;
window.doLoan     = doLoan;
window.openRet    = openRet;
window.closeMo    = closeMo;
window.doReturn   = doReturn;
window.toast      = toast;
