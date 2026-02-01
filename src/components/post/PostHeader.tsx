import Image from 'next/image';
import PostTags from './PostTags';

interface PostHeaderProps {
  title: string;
  thumbnail: string | null;
  createdAt: string;
  tags: string[];
}

export default function PostHeader({ title, thumbnail, createdAt, tags }: PostHeaderProps) {
  return (
    <div className="flex flex-col w-full">
      {thumbnail && (
        <Image
          src={thumbnail}
          alt="thumbnail"
          width={320}
          height={240}
          className="w-[20rem] object-contain"
        />
      )}

      <h1 className="text-[3rem] font-bold mt-8 text-content-light dark:text-content-dark">
        {title}
      </h1>

      <PostTags tags={tags} className="w-full mt-3" />

      <span className="text-[0.9rem] font-normal text-content-secondary-light dark:text-content-secondary-dark mt-4">
        {createdAt}
      </span>
    </div>
  );
}
