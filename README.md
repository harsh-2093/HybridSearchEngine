# Wiki Search Engine

A search engine built using Java and Spring Boot that explores how modern information retrieval systems work under the hood.

The project combines traditional keyword-based retrieval using **BM25** with semantic retrieval using **Sentence-BERT (SBERT)** to find relevant documents based on exact keyword matches and contextual meaning.

Rather than relying on prebuilt search engines such as Apache Lucene or Elasticsearch for ranking, the core BM25 scoring and ranking logic will be implemented independently.

## 🎯 Project Goals

- Understand Information Retrieval fundamentals.
- Implement BM25 ranking from scratch.
- Integrate SBERT for semantic search.
- Generate and compare document and query embeddings.
- Implement cosine similarity.
- Combine keyword-based and semantic retrieval.
- Implement Reciprocal Rank Fusion (RRF).
- Build a modular search engine using Java and Spring Boot.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Core programming language |
| Spring Boot | Backend application |
| Maven | Dependency management |
| REST API | Search interface |
| all-MiniLM-L6-v2 | Semantic embeddings |
| Cosine Similarity | Semantic similarity calculation |
| Git & GitHub | Version control |

## 🏗️ Architecture

```text
                  Documents
                      |
                      v
                 Preprocessing
                      |
             +--------+--------+
             |                 |
             v                 v
           BM25              SBERT
             |                 |
             v                 v
       Keyword Search    Semantic Search
             |                 |
             +--------+--------+
                      |
                      v
                 Hybrid Search
                      |
                      v
             Reciprocal Rank Fusion
                      |
                      v
                 Final Results
```

## 📚 Core Components

### 1. Document Processing

Documents are processed before retrieval.

- Lowercase conversion.
- Special character removal.
- Tokenization.
- Document length calculation.

### 2. BM25 Search

BM25 (Best Matching 25) is a probabilistic ranking function widely used in information retrieval systems.

It ranks documents based on query-term frequency, inverse document frequency, and document length normalization.

**BM25 Scoring Formula:**

$$
Score(D,Q) = \sum_{t \in Q} IDF(t) \cdot
\frac{f(t,D)(k_1+1)}
{f(t,D)+k_1\left(1-b+b\frac{|D|}{avgdl}\right)}
$$

**BM25 IDF Formula:**

$$
IDF(t) = \ln\left(1+\frac{N-df(t)+0.5}{df(t)+0.5}\right)
$$

Where:

- `f(t,D)` = Frequency of term `t` in document `D`.
- `|D|` = Document length.
- `avgdl` = Average document length across the collection.
- `N` = Total number of documents.
- `df(t)` = Number of documents containing term `t`.
- `k1` = Controls term frequency saturation.
- `b` = Controls document length normalization.

The BM25 scoring and ranking logic will be implemented from scratch without using Lucene or Elasticsearch.

### 3. Semantic Search with SBERT

Semantic search retrieves documents based on contextual meaning rather than relying exclusively on exact keyword matches.

The project will use **all-MiniLM-L6-v2** to generate dense vector embeddings for documents and search queries.

These embeddings represent the semantic meaning of text in numerical form.

**Cosine Similarity Formula:**

$$
Similarity(A,B) =
\frac{A \cdot B}
{\|A\| \times \|B\|}
$$

Where:

- `A` = Query embedding.
- `B` = Document embedding.
- `A · B` = Dot product of the vectors.
- `||A||` and `||B||` = Vector magnitudes.

Cosine similarity will help retrieve relevant documents even when the query and document use different words with similar meanings.

### 4. Hybrid Search

Hybrid search combines two retrieval approaches:

- **BM25:** Keyword-based retrieval.
- **SBERT:** Semantic retrieval.

The objective is to benefit from both exact keyword matching and contextual understanding.

### 5. Reciprocal Rank Fusion (RRF)

Reciprocal Rank Fusion combines ranked results from multiple retrieval systems into a unified ranking.

**RRF Formula:**

$$
RRF(d) = \sum_{r \in R}
\frac{1}{k + rank_r(d)}
$$

Where:

- `d` = Document.
- `R` = Set of ranking systems.
- `rank_r(d)` = Position of document `d` in ranking system `r`.
- `k` = Ranking constant, commonly 60.

RRF will combine BM25 and SBERT rankings to produce the final search results.

## 📂 Project Structure

```text
WikiSearchEngine/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/wikisearchengine/
│   │   │       │
│   │   │       ├── model/
│   │   │       │   └── Document.java
│   │   │       │
│   │   │       ├── preprocessing/
│   │   │       │   └── Tokenizer.java
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   └── DocumentRepository.java
│   │   │       │
│   │   │       ├── retrieval/
│   │   │       │   ├── DocumentFrequency.java
│   │   │       │   ├── IDFCalculator.java
│   │   │       │   ├── BM25.java
│   │   │       │   └── RRF.java
│   │   │       │
│   │   │       └── WikiSearchEngineApplication.java
│   │   │
│   │   └── resources/
│   │
│   └── test/
│
├── pom.xml
└── README.md
```

*The structure will evolve as the project develops.*

## 🚀 Getting Started

### Prerequisites

- Java 21 or later.
- Maven 3.8+.
- Git.

### Clone the Repository

```bash
git clone https://github.com/harsh-2093/WikiSearchEngine.git
cd WikiSearchEngine
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

## 🧪 Testing

The project will include tests for individual retrieval components.

Planned test coverage:

- Tokenization.
- Document frequency calculation.
- IDF calculation.
- BM25 scoring and ranking.
- SBERT embedding generation.
- Cosine similarity.
- Semantic search.
- Hybrid ranking.
- Reciprocal Rank Fusion.

## 🗺️ Development Roadmap

- [x] Initialize Spring Boot project.
- [x] Create Document model.
- [x] Implement tokenizer.
- [x] Create in-memory document repository.
- [x] Implement Document Frequency calculation.
- [x] Implement BM25 IDF calculation.
- [X] Implement BM25 scoring from scratch.
- [X] Build document indexing pipeline.
- [X] Integrate SBERT using all-MiniLM-L6-v2.
- [X] Implement embedding generation.
- [X] Implement cosine similarity.
- [X] Develop semantic search.
- [ ] Implement hybrid retrieval.
- [ ] Implement Reciprocal Rank Fusion.
- [ ] Expose search functionality through REST APIs.
- [ ] Add comprehensive test coverage.
- [ ] Evaluate retrieval quality using sample queries.

## 💡 Learning Outcomes

Through this project, the objective is to gain practical understanding of:

- Information Retrieval fundamentals.
- Probabilistic ranking models.
- BM25 implementation.
- Dense vector representations.
- Sentence embeddings.
- Semantic similarity.
- Hybrid information retrieval.
- Reciprocal Rank Fusion.
- Backend development using Java and Spring Boot.

## 📌 Project Status

**Under Development**

This project is being developed incrementally, with an emphasis on understanding and implementing the underlying retrieval algorithms rather than relying entirely on existing search-engine libraries.

---

**Author:** Harsh Tripathi

**GitHub:** [harsh-2093](https://github.com/harsh-2093)
