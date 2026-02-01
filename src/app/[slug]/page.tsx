import { Metadata } from 'next';
import { notFound } from 'next/navigation';
import { getAllPosts, getPostBySlug } from '@/lib/posts';
import { getThumbnailPath } from '@/lib/utils';
import PostHeader from '@/components/post/PostHeader';
import MarkdownContent from '@/components/MarkdownContent';
import PostComments from '@/components/post/PostComments';

interface PostPageProps {
  params: Promise<{
    slug: string;
  }>;
}

export async function generateStaticParams() {
  const posts = getAllPosts();
  return posts.map((post) => ({
    slug: post.slug,
  }));
}

export async function generateMetadata({ params }: PostPageProps): Promise<Metadata> {
  const { slug } = await params;
  const post = getPostBySlug(slug);

  if (!post) {
    return {
      title: 'Not Found - Jin Blog',
    };
  }

  return {
    title: `${post.title} - Jin Blog`,
    description: post.description,
  };
}

export default async function PostPage({ params }: PostPageProps) {
  const { slug } = await params;
  const post = getPostBySlug(slug);

  if (!post) {
    notFound();
  }

  const thumbnail = getThumbnailPath(slug);

  return (
    <article className="w-full px-4">
      <PostHeader
        title={post.title}
        thumbnail={thumbnail}
        createdAt={post.createdAt}
        tags={post.tags}
      />

      <div className="mt-16">
        <MarkdownContent content={post.content} />
      </div>

      <PostComments />
    </article>
  );
}
