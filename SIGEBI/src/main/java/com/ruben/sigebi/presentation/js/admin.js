// =============================================
//  SIGEBI — Admin Panel JavaScript
// =============================================

import {
  getAllResources,
  requestLoan,
  getUserLoans,
  returnLoan,
  getUserPenalties,
  applyPenalty,
  removePenalty,
  addResource    as apiAddResource,
  searchResource
} from "./api.js";

/* ── MOCK DATA ── */
const ADMIN_RES = [
  { id:'a1b2', title:'Clean Code',              author:'Robert C. Martin',    type:'BOOK',    isbn:'978-0132350884', total:3, avail:3 },
  { id:'e5f6', title:'Domain-Driven Design',    author:'Eric Evans',          type:'BOOK',    isbn:'978-0321125217', total:2, avail:1 },
  { id:'i9j0', title:'El Quijote',              author:'Miguel de Cervantes', type:'BOOK',    isbn:'978-8420412146', total:2, avail:0 },
  { id:'m3n4', title:'Introducción a Algoritmos',author:'Cormen et al.',      type:'BOOK',    isbn:'978-0262033848', total:4, avail:2 },
  { id:'q7r8', title:'Revista ACMEI Vol.12',    author:'ACMEI',               type:'JOURNAL', isbn:'—',              total:1, avail:1 },
  { id:'u1v2', title:'Spring Boot en Acción',   author:'Craig Walls',         type:'BOOK',    isbn:'978-1617294945', total:5, avail:4 },
];

const ALL_LOANS = [
  { id:'l001', user:'Ruben Ureña', resource:'Clean Code',   copy:'c001', start:'2025-03-15', due:'2025-03-22', state:'OVERDUE' },
  { id:'l002', user:'Ruben Ureña', resource:'DDD',          copy:'c002', start:'2025-04-08', due:'2025-04-15', state:'ON_TIME' },
  { id:'l003', user:'Ana Pérez',   resource:'GoF Patterns', copy:'c007', start:'2025-04-01', due:'2025-04-08', state:'OVERDUE' },
  { id:'l004', user:'Carlos Mena', resource:'Algoritmos',   copy:'c010', start:'2025-04-10', due:'2025-04-17', state:'ON_TIME' },
];

const PENALTIES = [
  { id:'p001', user:'Ruben Ureña', userId:'u001', book:'Clean Code',   loan:'l001', start:'2025-03-23', end:'2025-03-30' },
  { id:'p002', user:'Ana Pérez',   userId:'u002', book:'GoF Patterns', loan:'l003', start:'2025-04-09', end:'2025-04-16' },
];

const USERS = [
  { id:'u001', name:'Ruben Ureña', email:'ruben@sigebi.do',  state:'PENALIZE', roles:['STUDENT'],    loans:2 },
  { id:'u002', name:'Ana Pérez',   email:'ana@sigebi.do',    state:'PENALIZE', roles:['STUDENT'],    loans:1 },
  { id:'u003', name:'Carlos Mena', email:'carlos@sigebi.do', state:'ELIGIBLE', roles:['LIBRARIAN'],  loans:1 },
  { id:'u004', name:'Maria López', email:'maria@sigebi.do',  state:'ELIGIBLE', roles:['STUDENT'],    loans:0 },
];

const ROLES = [
  { id:'r001', name:'LIBRARIAN', perms:['RESOURCE:ADD','RESOURCE:EDIT','RESOURCE:DELETE','USER:VIEW'] },
  { id:'r002', name:'STUDENT',   perms:['RESOURCE:VIEW','LOAN:REQUEST','LOAN:RETURN'] },
  { id:'r003', name:'ADMIN',     perms:['USER:APPLY_PENALTY','USER:REMOVE_PENALTY','USER:ASSIGN_ROLE','USER:ASSIGN_PERMISSION','RESOURCE:ADD','RESOURCE:DELETE'] },
];

let rmPenId = null;

/* ── NAVIGATION ── */
export function sp(page, btn) {
  document.querySelectorAll('.pg').forEach(p => p.classList.remove('on'));
  document.querySelectorAll('.sn-item').forEach(s => s.classList.remove('on'));
  document.getElementById('pg-' + page).classList.add('on');
  if (btn) btn.classList.add('on');

  const titles = {
    dashboard:        'Dashboard',
    recursos:         'Inventario de Recursos',
    'nuevo-recurso':  'Agregar Recurso',
    'todos-prestamos':'Todos los Préstamos',
    penalizaciones:   'Penalizaciones',
    usuarios:         'Usuarios',
    roles:            'Roles y Permisos',
  };
  document.getElementById('tb-title').textContent = titles[page] || page;

  if (page === 'recursos')          renderAdminRes(ADMIN_RES);
  if (page === 'todos-prestamos')   renderAllLoans(ALL_LOANS);
  if (page === 'penalizaciones')    renderAdminPens();
  if (page === 'usuarios')          renderUsers(USERS);
  if (page === 'roles')             renderRoles();
}

/* ── RESOURCES ── */
function renderAdminRes(list) {
  const tb = document.getElementById('admin-res-tbody');
  if (!list.length) {
    tb.innerHTML = '<tr><td colspan="7"><div class="empty">Sin resultados</div></td></tr>';
    return;
  }
  tb.innerHTML = list.map(r => `
    <tr>
      <td><div class="tt">${r.title}</div><div class="ts">${r.author}</div></td>
      <td>${r.type}</td>
      <td style="font-family:monospace;font-size:.75rem">${r.isbn}</td>
      <td style="font-weight:500;color:var(--text)">${r.total}</td>
      <td style="font-weight:500;color:${r.avail > 0 ? 'var(--green)' : 'var(--red)'}">${r.avail}</td>
      <td><span class="b ${r.avail > 0 ? 'bav' : 'blo'}">${r.avail > 0 ? 'Disponible' : 'Sin copias'}</span></td>
      <td style="display:flex;gap:.4rem">
        <button class="btn bs btn-sm">Editar</button>
        <button class="btn bd btn-sm" onclick="toast('Recurso eliminado del inventario','s')">Eliminar</button>
      </td>
    </tr>`).join('');
}

export function filterAdminRes() {
  const q = document.getElementById('q-res').value.toLowerCase();
  const t = document.getElementById('q-res-type').value;
  renderAdminRes(
    ADMIN_RES.filter(r =>
      (!q || r.title.toLowerCase().includes(q) || r.author.toLowerCase().includes(q)) &&
      (!t || r.type === t)
    )
  );
}

/* ── ALL LOANS ── */
function renderAllLoans(list) {
  const tb = document.getElementById('all-loans-tbody');
  tb.innerHTML = list.map(l => `
    <tr>
      <td><div class="tt">${l.user}</div></td>
      <td><div class="tt">${l.resource}</div><div class="ts">Copia: ${l.copy}</div></td>
      <td>${l.start}</td>
      <td style="color:${l.state === 'OVERDUE' ? 'var(--red)' : 'var(--text)'};font-weight:500">${l.due}</td>
      <td><span class="b ${l.state === 'OVERDUE' ? 'bov' : 'bok'}">${l.state === 'OVERDUE' ? 'Vencido' : 'Al día'}</span></td>
      <td>${l.state === 'OVERDUE'
        ? `<button class="btn bd btn-sm" onclick="openPen('${l.user}','${l.id}')">Penalizar</button>`
        : `<button class="btn bs btn-sm" disabled>—</button>`
      }</td>
    </tr>`).join('');
}

export function filterLoans() {
  const q = document.getElementById('q-loans').value.toLowerCase();
  const s = document.getElementById('q-loan-state').value;
  renderAllLoans(
    ALL_LOANS.filter(l =>
      (!q || l.user.toLowerCase().includes(q) || l.resource.toLowerCase().includes(q)) &&
      (!s || l.state === s)
    )
  );
}

/* ── PENALTIES (ADMIN) ── */
function renderAdminPens() {
  const el = document.getElementById('pen-admin-list');
  if (!PENALTIES.length) {
    el.innerHTML = '<div class="empty">Sin penalizaciones activas</div>';
    return;
  }
  el.innerHTML = PENALTIES.map(p => `
    <div class="pc">
      <div class="pc-info">
        <div class="pc-title">${p.user} — ${p.book}</div>
        <div class="pc-meta">Desde ${p.start} hasta ${p.end} · Préstamo: ${p.loan}</div>
      </div>
      <div class="pc-actions">
        <span class="b bov">Activa</span>
        <button class="btn bg btn-sm" onclick="openRmPen('${p.id}')">Remover</button>
      </div>
    </div>`).join('');
}

/* ── USERS ── */
function renderUsers(list) {
  const tb = document.getElementById('users-tbody');
  tb.innerHTML = list.map(u => `
    <tr>
      <td><div class="tt">${u.name}</div></td>
      <td style="font-size:.78rem;color:var(--muted)">${u.email}</td>
      <td><span class="b ${u.state === 'PENALIZE' ? 'bov' : 'bav'}">${u.state === 'PENALIZE' ? 'Penalizado' : 'Elegible'}</span></td>
      <td>${u.roles.map(r => `<span class="perm-pill">${r}</span>`).join(' ')}</td>
      <td style="font-weight:500;color:var(--text)">${u.loans}</td>
      <td><button class="btn bs btn-sm" onclick="sp('roles')">Gestionar roles</button></td>
    </tr>`).join('');
}

export function filterUsers(e) {
  const q = e.target.value.toLowerCase();
  renderUsers(USERS.filter(u =>
    u.name.toLowerCase().includes(q) || u.email.toLowerCase().includes(q)
  ));
}

/* ── ROLES ── */
function renderRoles() {
  const el = document.getElementById('roles-list');
  el.innerHTML = ROLES.map(r => `
    <div class="ri">
      <div class="ri-name"><span class="b bac" style="margin-right:.5rem">${r.name}</span></div>
      <div class="ri-perms">${r.perms.map(p => `<span class="perm-pill">${p}</span>`).join('')}</div>
    </div>`).join('');
}

/* ── ADD RESOURCE ── */
export function addResource() {
  const title = document.getElementById('nr-title').value;
  const qty   = document.getElementById('nr-qty').value;
  if (!title) { toast('El título es obligatorio', 'e'); return; }
  toast(`Recurso "${title}" creado con ${qty} cop(ias)`, 's');
  ['nr-title','nr-sub','nr-isbn','nr-authors'].forEach(id => document.getElementById(id).value = '');
  document.getElementById('nr-qty').value = '1';
  setTimeout(() => sp('recursos'), 900);
}

/* ── PENALTIES ── */
export function openPen(user = '', loanId = '') {
  if (user) document.getElementById('pen-user').value = user;
  document.getElementById('pen-modal').classList.add('op');
}

export function doPenalty() {
  const u = document.getElementById('pen-user').value;
  if (!u) { toast('Indica el usuario', 'e'); return; }
  closeMo('pen-modal');
  toast(`Penalización aplicada a ${u}`, 's');
}

export function openRmPen(id) {
  rmPenId = id;
  document.getElementById('rm-pen-modal').classList.add('op');
}

export function doRemovePen() {
  const i = PENALTIES.findIndex(p => p.id === rmPenId);
  if (i !== -1) PENALTIES.splice(i, 1);
  closeMo('rm-pen-modal');
  toast('Penalización removida. Usuario habilitado', 's');
  renderAdminPens();
}

/* ── ASSIGN ROLE ── */
export function assignRole() {
  const u = document.getElementById('role-user-id').value;
  const r = document.getElementById('role-id').value;
  if (!u) { toast('Selecciona un usuario', 'e'); return; }
  const usr = USERS.find(x => x.id === u);
  const rol = ROLES.find(x => x.id === r);
  if (usr && rol && !usr.roles.includes(rol.name)) usr.roles.push(rol.name);
  toast(`Rol ${rol?.name} asignado a ${usr?.name}`, 's');
}

/* ── UTILS ── */
export function closeMo(id) {
  document.getElementById(id).classList.remove('op');
}

export function toast(msg, type = 'ok') {
  const a = document.getElementById('ta');
  const t = document.createElement('div');
  t.className = 't ' + type;
  t.textContent = msg;
  a.appendChild(t);
  setTimeout(() => t.remove(), 3000);
}

/* ── INIT & GLOBAL EXPOSURE ── */
renderAdminRes(ADMIN_RES);

// Expose to global scope (required for inline onclick handlers with type="module")
window.sp            = sp;
window.filterAdminRes = filterAdminRes;
window.filterLoans   = filterLoans;
window.filterUsers   = filterUsers;
window.openPen       = openPen;
window.doPenalty     = doPenalty;
window.openRmPen     = openRmPen;
window.doRemovePen   = doRemovePen;
window.addResource   = addResource;
window.assignRole    = assignRole;
window.closeMo       = closeMo;
window.toast         = toast;
