// Minimal mock API for the Forms app.
// Run:  npm init -y && npm install express && node server.js
const express = require("express");

const app = express();
const PORT = 3000;

const forms = [
  {
    id: "1",
    title: "Customer Feedback",
    description: "Tell us how we're doing",
    url: "https://example.com/",
  },
  {
    id: "2",
    title: "Event Registration",
    description: "Sign up for the next event",
    url: "https://www.wikipedia.org/",
  },
  {
    id: "3",
    title: "Support Request",
    description: "Report an issue or ask for help",
    url: "https://developer.android.com/",
  },
];

app.get("/forms", (req, res) => {
  res.json(forms);
});

// Listen on all interfaces so the emulator (10.0.2.2) and real devices can reach it.
app.listen(PORT, "0.0.0.0", () => {
  console.log(`Mock API running on http://localhost:${PORT}/forms`);
});