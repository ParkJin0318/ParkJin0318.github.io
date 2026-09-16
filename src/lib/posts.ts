import fs from 'fs';
import path from 'path';
import matter from 'gray-matter';
import { Post } from '@/types/post';

const postsDirectory = path.join(process.cwd(), 'content/posts');

export function getAllPosts(): Post[] {
  const fileNames = fs.readdirSync(postsDirectory);
  const posts = fileNames
    .filter((fileName) => fileName.endsWith('.md'))
    .map((fileName) => {
      const slug = fileName.replace(/\.md$/, '');
      const fullPath = path.join(postsDirectory, fileName);
      const fileContents = fs.readFileSync(fullPath, 'utf8');
      const { data, content } = matter(fileContents);

      return {
        slug,
        category: data.category || '',
        title: data.title || '',
        description: data.description || '',
        createdAt: data.createdAt || '',
        tags: data.tags || [],
        content,
      } as Post;
    });

  // Sort by createdAt descending (newest first)
  return posts.sort((a, b) => {
    const dateA = a.createdAt.replace(/\./g, '');
    const dateB = b.createdAt.replace(/\./g, '');
    return dateB.localeCompare(dateA);
  });
}

export function getPostBySlug(slug: string): Post | null {
  try {
    const fullPath = path.join(postsDirectory, `${slug}.md`);
    const fileContents = fs.readFileSync(fullPath, 'utf8');
    const { data, content } = matter(fileContents);

    return {
      slug,
      category: data.category || '',
      title: data.title || '',
      description: data.description || '',
      createdAt: data.createdAt || '',
      tags: data.tags || [],
      content,
    };
  } catch {
    return null;
  }
}

export function getAllCategories(): string[] {
  const posts = getAllPosts();
  return [...new Set(posts.map((post) => post.category))];
}

export function getAllTags(): string[] {
  const posts = getAllPosts();
  return [...new Set(posts.flatMap((post) => post.tags))];
}
