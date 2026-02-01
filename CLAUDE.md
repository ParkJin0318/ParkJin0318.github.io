# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Personal blog website built with **Next.js 14** (App Router) + **TypeScript** + **Tailwind CSS**, deployed to GitHub Pages. Blog posts are written in Markdown with YAML frontmatter.

## Development Commands

```bash
# Start development server (http://localhost:3000)
npm run dev

# Build for production (static export)
npm run build

# Preview production build
npm run start
```

## Architecture

### Component Hierarchy

```
layout.tsx (root layout)
└── ThemeProvider (dark/light mode context)
    └── NavHeader (sticky nav with theme toggle)
        └── [Page Content]
            ├── page.tsx - Home page with PostCard list, TabRow (categories), PostTags
            └── [slug]/page.tsx - Dynamic post pages
```

### Key Directories

- `src/app/` - Next.js App Router pages
- `src/components/` - Reusable UI components
  - `NavHeader.tsx` - Navigation header
  - `TabRow.tsx` - Category tabs
  - `post/` - Post-related components (PostCard, PostHeader, PostTags, PostComments)
  - `MarkdownContent.tsx` - Markdown renderer with syntax highlighting
- `src/contexts/` - React contexts (ThemeContext for dark/light mode)
- `src/lib/` - Utility functions (posts.ts for markdown processing)
- `src/types/` - TypeScript type definitions
- `content/posts/` - Blog post markdown files
- `public/` - Static assets (fonts, images, highlight.js)

### Data Flow

- `getAllPosts()` reads all markdown files and parses frontmatter using gray-matter
- Posts are filtered by category/tag on the home page (client-side)
- Color mode persisted to localStorage (key: "blog:colorMode")

### Blog Post Format

Markdown files in `content/posts/` with frontmatter:

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
```

Thumbnail images: `/images/{slug-with-underscores}/thumbnail.jpg`

## Theme System

Colors defined in `tailwind.config.ts`. Light/dark mode toggle in NavHeader uses ThemeContext.

Color palette:
- Primary: #296FCF
- Light mode: bg #FFFFFF, content #191f28
- Dark mode: bg #2B2B2B, content #FFFFFF

## Deployment

GitHub Actions (`.github/workflows/deploy.yml`) auto-deploys on push to `main`:
- Uses Node.js 20
- Runs `npm ci` and `npm run build`
- Exports static site to `out/`
- Deploys to GitHub Pages
