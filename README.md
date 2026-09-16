# Jin Blog

Personal blog website built with Next.js 14, TypeScript, and Tailwind CSS.

## Getting Started

First, install dependencies:

```bash
npm install
```

Then, run the development server:

```bash
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

## Project Structure

```
├── src/
│   ├── app/              # Next.js App Router pages
│   ├── components/       # Reusable UI components
│   ├── contexts/         # React contexts (theme)
│   ├── lib/              # Utility functions
│   └── types/            # TypeScript types
├── content/
│   └── posts/            # Markdown blog posts
├── public/               # Static assets
└── ...
```

## Writing Blog Posts

Create a new markdown file in `content/posts/` with the following frontmatter:

```yaml
---
category: 개발
title: Post Title
description: Post description
createdAt: 2024.07.08
tags:
  - Tag1
  - Tag2
---

Your content here...
```

Add a thumbnail image at `/public/images/{filename-with-underscores}/thumbnail.jpg`

## Build for Production

```bash
npm run build
```

The static site will be exported to the `out/` directory.

## Deployment

The site is automatically deployed to GitHub Pages when pushing to the `main` branch via GitHub Actions.
