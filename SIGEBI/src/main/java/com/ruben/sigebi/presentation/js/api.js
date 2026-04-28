const API = "http://localhost:8080/api";

/* =======================
   RESOURCES
======================= */

export async function getAllResources() {
  const res = await fetch(`${API}/resources`);
  return await res.json();
}

export async function searchResource(data) {
  const res = await fetch(`${API}/resources/search`, {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(data)
  });
  return await res.json();
}

export async function addResource(resource) {
  const res = await fetch(`${API}/resources`, {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(resource)
  });
  return await res.json();
}

/* =======================
   LOANS
======================= */

export async function getUserLoans(userId) {
  const res = await fetch(`${API}/loans/user/${userId}`);
  return await res.json();
}

export async function requestLoan(userId, resourceId) {
  const res = await fetch(`${API}/loans/user/${userId}`, {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({
      resourceID: resourceId //  EXACTO
    })
  });
  return await res.json();
}

export async function returnLoan(loanId) {
  const res = await fetch(`${API}/loans/${loanId}/return`, {
    method: "PUT"
  });
  return await res.json();
}

/* =======================
    PENALTIES
======================= */

export async function getUserPenalties(userId) {
  const res = await fetch(`${API}/penalties/user/${userId}`);
  return await res.json();
}

export async function applyPenalty(borrowerId, adminId) {
  const res = await fetch(`${API}/penalties/apply`, {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({
      borrowerId,
      adminId
    })
  });
  return await res.json();
}

export async function removePenalty(penaltyId, adminId) {
  const res = await fetch(`${API}/penalties/${penaltyId}`, {
    method: "DELETE",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({
      penaltyId,
      adminId
    })
  });
  return await res.json();
}