import { Metadata } from 'next';
import { notFound } from 'next/navigation';
import { getAllPosts, getPostBySlug } from '@/lib/posts';
import PostHeader from '@/components/post/PostHeader';
import MarkdownContent from '@/components/MarkdownContent';
import PostComments from '@/components/post/PostComments';

const SITE_URL = 'https://parkjin0318.github.io';

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
      title: 'Not Found',
    };
  }

  const thumbnailPath = `/images/${slug.replace(/-/g, '_')}/thumbnail.jpg`;

  return {
    title: post.title,
    description: post.description,
    keywords: post.tags,
    openGraph: {
      type: 'article',
      title: post.title,
      description: post.description,
      url: `${SITE_URL}/${slug}`,
      images: [
        {
          url: thumbnailPath,
          width: 1200,
          height: 630,
          alt: post.title,
        },
      ],
      publishedTime: post.createdAt.replace(/\./g, '-'),
      tags: post.tags,
    },
    twitter: {
      card: 'summary_large_image',
      title: post.title,
      description: post.description,
      images: [thumbnailPath],
    },
  };
}

function generateJsonLd(post: { title: string; description: string; createdAt: string; slug: string; tags: string[] }) {
  return {
    '@context': 'https://schema.org',
    '@type': 'BlogPosting',
    headline: post.title,
    description: post.description,
    datePublished: post.createdAt.replace(/\./g, '-'),
    author: {
      '@type': 'Person',
      name: 'Jin Park',
    },
    url: `${SITE_URL}/${post.slug}`,
    keywords: post.tags.join(', '),
  };
}

export default async function PostPage({ params }: PostPageProps) {
  const { slug } = await params;
  const post = getPostBySlug(slug);

  if (!post) {
    notFound();
  }

  const jsonLd = generateJsonLd(post);

  return (
    <>
      <script
        type="application/ld+json"
        dangerouslySetInnerHTML={{ __html: JSON.stringify(jsonLd) }}
      />
      <article className="w-full max-w-[50rem] mx-auto px-6 py-12">
        <PostHeader
          title={post.title}
          createdAt={post.createdAt}
        />

        <MarkdownContent content={post.content} />

        <PostComments />
      </article>
    </>
  );
}
