import { useState } from "react";
import api from "./api";

function App() {
  const [language, setLanguage] = useState("java");
  const [code, setCode] = useState("");
  const [review, setReview] = useState("");
  const [loading, setLoading] = useState(false);

  const handleReview = async () => {
    try {
      setLoading(true);

      const response = await api.post("/api/reviews", {
        language,
        code,
      });

      setReview(response.data.review);
    } catch (error) {
      console.error(error);
      setReview("Failed to generate review");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ padding: "20px", maxWidth: "1000px", margin: "auto" }}>
      <h1>AI Code Review System </h1>

      <select
        value={language}
        onChange={(e) => setLanguage(e.target.value)}
      >
        <option value="java">Java</option>
        <option value="javascript">JavaScript</option>
        <option value="python">Python</option>
      </select>

      <br />
      <br />

      <textarea
        rows="15"
        cols="100"
        placeholder="Paste your code here..."
        value={code}
        onChange={(e) => setCode(e.target.value)}
      />

      <br />
      <br />

      <button onClick={handleReview}>
        {loading ? "Reviewing..." : "Review Code"}
      </button>

      <hr />

      <h2>AI Review Result</h2>

      <pre
        style={{
          whiteSpace: "pre-wrap",
          backgroundColor: "#f4f4f4",
          padding: "15px",
          borderRadius: "8px",
        }}
      >
        {review}
      </pre>
    </div>
  );
}

export default App;