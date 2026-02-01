'use client';

import Image from 'next/image';
import { Post } from '@/types/post';
import { getThumbnailPath } from '@/lib/utils';

interface PostCardProps {
  post: Post;
  onClick: () => void;
}

export default function PostCard({ post, onClick }: PostCardProps) {
  const thumbnailPath = getThumbnailPath(post.slug);

  return (
    <div
      onClick={onClick}
      className="flex w-full p-[1.03rem] cursor-pointer transition-all duration-200 ease-in-out hover:scale-[1.03] text-content-light dark:text-content-dark hover:text-primary"
    >
      <div className="flex flex-col flex-1">
        <span className="text-[1.25rem] font-bold">{post.title}</span>
        <span className="text-[1rem] font-normal text-content-secondary-light dark:text-content-secondary-dark mt-2">
          {post.description}
        </span>
        <span className="text-[0.9rem] font-normal text-content-secondary-light dark:text-content-secondary-dark mt-2">
          {post.createdAt}
        </span>
      </div>
      <Image
        src={thumbnailPath}
        alt="thumbnail"
        width={130}
        height={100}
        className="w-[8.125rem] h-[6.25rem] ml-2 object-contain"
      />
    </div>
  );
}
