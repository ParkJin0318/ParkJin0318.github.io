'use client';

import { useState, useMemo } from 'react';
import { useRouter } from 'next/navigation';
import TabRow from '@/components/TabRow';
import PostCard from '@/components/post/PostCard';
import PostTags from '@/components/post/PostTags';
import { Post } from '@/types/post';

interface HomeContentProps {
  posts: Post[];
}

export default function HomeContent({ posts }: HomeContentProps) {
  const router = useRouter();

  const categories = useMemo(() => {
    return [...new Set(posts.map((post) => post.category))];
  }, [posts]);

  const allTags = useMemo(() => {
    return [...new Set(posts.flatMap((post) => post.tags))];
  }, [posts]);

  const [selectedCategory, setSelectedCategory] = useState<string | null>(
    categories[0] || null
  );
  const [selectedTag, setSelectedTag] = useState<string | null>(null);

  const filteredPosts = useMemo(() => {
    return posts
      .filter((post) => post.category === selectedCategory)
      .filter((post) => (selectedTag ? post.tags.includes(selectedTag) : true));
  }, [posts, selectedCategory, selectedTag]);

  const handleCategorySelect = (category: string) => {
    setSelectedCategory(category);
    setSelectedTag(null);
  };

  const handleTagClick = (tag: string) => {
    setSelectedTag((prev) => (prev === tag ? null : tag));
  };

  return (
    <div className="flex w-full mt-8 mb-16">
      <div className="flex flex-col flex-1">
        <TabRow
          tabs={categories}
          selectedTab={selectedCategory}
          onTabSelected={handleCategorySelect}
        />

        {filteredPosts.map((post) => (
          <PostCard
            key={post.slug}
            post={post}
            onClick={() => router.push(`/${post.slug}`)}
          />
        ))}
      </div>

      <div className="hidden md:block h-auto mx-8 border-l border-divider-light dark:border-divider-dark" />

      <div className="hidden md:flex flex-col max-w-[20rem]">
        <span className="text-[0.9rem] font-normal text-content-secondary-light dark:text-content-secondary-dark">
          태그
        </span>

        <PostTags
          tags={allTags}
          selectedTag={selectedTag}
          onClick={handleTagClick}
          className="mt-4"
        />
      </div>
    </div>
  );
}
