'use client';

import { Post } from '@/types/post';

interface PostCardProps {
  post: Post;
  onClick: () => void;
}

export default function PostCard({ post, onClick }: PostCardProps) {
  return (
    <article
      onClick={onClick}
      className="group cursor-pointer py-8 border-b border-divider-light dark:border-divider-dark last:border-b-0"
    >
      <h2 className="text-xl font-bold text-content-light dark:text-content-dark group-hover:text-primary transition-colors">
        {post.title}
      </h2>
      <p className="mt-3 text-base text-content-secondary-light dark:text-content-secondary-dark line-clamp-2">
        {post.description}
      </p>
      <time className="mt-4 block text-sm text-content-secondary-light dark:text-content-secondary-dark">
        {post.createdAt}
      </time>
    </article>
  );
}
