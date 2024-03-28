<script setup>
import Image from '@tiptap/extension-image'
import { Placeholder } from '@tiptap/extension-placeholder'
import Underline from '@tiptap/extension-underline'
import StarterKit from '@tiptap/starter-kit'
import HardBreak from '@tiptap/extension-hard-break'
import { defineEmits, defineProps, ref, watch } from 'vue'

import { EditorContent, useEditor, Editor } from '@tiptap/vue-3'
import CodeIcon from 'vue-material-design-icons/CodeTags.vue'
import BoldIcon from 'vue-material-design-icons/FormatBold.vue'
import H1Icon from 'vue-material-design-icons/FormatHeader1.vue'
import H2Icon from 'vue-material-design-icons/FormatHeader2.vue'
import H3Icon from 'vue-material-design-icons/FormatHeader3.vue'
import H4Icon from 'vue-material-design-icons/FormatHeader4.vue'
import H5Icon from 'vue-material-design-icons/FormatHeader5.vue'
import H6Icon from 'vue-material-design-icons/FormatHeader6.vue'

import ItalicIcon from 'vue-material-design-icons/FormatItalic.vue'
import ListIcon from 'vue-material-design-icons/FormatListBulleted.vue'
import OrderedListIcon from 'vue-material-design-icons/FormatListNumbered.vue'
import BlockquoteIcon from 'vue-material-design-icons/FormatQuoteClose.vue'
import UnderlineIcon from 'vue-material-design-icons/FormatUnderline.vue'
import HorizontalRuleIcon from 'vue-material-design-icons/Minus.vue'
import RedoIcon from 'vue-material-design-icons/Redo.vue'
import UndoIcon from 'vue-material-design-icons/Undo.vue'
import { onMounted } from 'vue'
import { onBeforeMount } from 'vue'

const props = defineProps({
  modelValue: String,
})

const emit = defineEmits(['update:modelValue'])

const CustomHardBreak = HardBreak.extend({
  addKeyboardShortcuts() {
    return {
      Enter: () => this.editor.commands.setHardBreak(),
    }
  },
})

const editor = new Editor({
  content: props.modelValue,
  onUpdate: ({ editor }) => {
    // console.log(editor.getHTML())
    emit('update:modelValue', editor.getHTML())
  },
  extensions: [
    StarterKit,
    Underline,
    Placeholder.configure({
      placeholder: 'content',
    }),
    Image.configure({
      inline: true,
    }),
    CustomHardBreak,
  ],
  editorProps: {
    attributes: {
      class: 'p-2 outline-none prose max-w-none',
    },
  },
})

watch(
  () => props.modelValue,
  value => {
    if (editor && editor.getHTML() !== value) {
      editor.commands.setContent(value, false)
    }
  },
)
</script>

<template>
  <div style="border: 1px solid">
    <div
      v-if="editor"
      class="buttons text-gray-700 flex items-center flex-wrap gap-x-4 border border-l border-r border-gray-400 p-4"
      style="display: flex"
    >
      <button
        type="button"
        @click="editor.chain().focus().toggleBold().run()"
        :class="{ 'bg-gray-200 rounded': editor.isActive('bold') }"
        class="p-1"
      >
        <BoldIcon title="Bold" />
      </button>
      <button
        type="button"
        @click="editor.chain().focus().toggleItalic().run()"
        :class="{ 'bg-gray-200 rounded': editor.isActive('italic') }"
        class="p-1"
      >
        <ItalicIcon title="Italic" />
      </button>

      <button
        type="button"
        @click="editor.chain().focus().toggleUnderline().run()"
        :class="{ 'bg-gray-200 rounded': editor.isActive('underline') }"
        class="p-1"
      >
        <UnderlineIcon title="Underline" />
      </button>

      <button
        type="button"
        @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
        :class="{
          'bg-gray-200 rounded': editor.isActive('heading', { level: 1 }),
        }"
        class="p-1"
      >
        <H1Icon title="H1" />
      </button>
      <button
        type="button"
        @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
        :class="{
          'bg-gray-200 rounded': editor.isActive('heading', { level: 2 }),
        }"
        class="p-1"
      >
        <H2Icon title="H2" />
      </button>
      <button
        type="button"
        @click="editor.chain().focus().toggleHeading({ level: 3 }).run()"
        :class="{
          'bg-gray-200 rounded': editor.isActive('heading', { level: 3 }),
        }"
        class="p-1"
      >
        <H3Icon title="H3" />
      </button>
      <button
        type="button"
        @click="editor.chain().focus().toggleHeading({ level: 4 }).run()"
        :class="{
          'bg-gray-200 rounded': editor.isActive('heading', { level: 4 }),
        }"
        class="p-1"
      >
        <H4Icon title="H4" />
      </button>

      <button
        type="button"
        @click="editor.chain().focus().toggleHeading({ level: 5 }).run()"
        :class="{
          'bg-gray-200 rounded': editor.isActive('heading', { level: 5 }),
        }"
        class="p-1"
      >
        <H5Icon title="H5" />
      </button>
      <button
        type="button"
        @click="editor.chain().focus().toggleHeading({ level: 6 }).run()"
        :class="{
          'bg-gray-200 rounded': editor.isActive('heading', { level: 6 }),
        }"
        class="p-1"
      >
        <H6Icon title="H6" />
      </button>
      <button
        type="button"
        @click="editor.chain().focus().toggleBulletList().run()"
        :class="{ 'bg-gray-200 rounded': editor.isActive('bulletList') }"
        class="p-1"
      >
        <ListIcon title="Bullet List" />
      </button>
      <button
        type="button"
        @click="editor.chain().focus().toggleOrderedList().run()"
        :class="{ 'bg-gray-200 rounded': editor.isActive('orderedList') }"
        class="p-1"
      >
        <OrderedListIcon title="Ordered List" />
      </button>
      <button
        type="button"
        @click="editor.chain().focus().toggleBlockquote().run()"
        :class="{ 'bg-gray-200 rounded': editor.isActive('blockquote') }"
        class="p-1"
      >
        <BlockquoteIcon title="Blockquote" />
      </button>
      <button
        type="button"
        @click="editor.chain().focus().toggleCode().run()"
        :class="{ 'bg-gray-200 rounded': editor.isActive('code') }"
        class="p-1"
      >
        <CodeIcon title="Code" />
      </button>
      <button
        type="button"
        @click="editor.chain().focus().setHorizontalRule().run()"
        class="p-1"
      >
        <HorizontalRuleIcon title="Horizontal Rule" />
      </button>
      <button
        type="button"
        class="p-1 disabled:text-gray-400"
        @click="editor.chain().focus().undo().run()"
        :disabled="!editor.can().chain().focus().undo().run()"
      >
        <UndoIcon title="Undo" />
      </button>
      <button
        type="button"
        @click="editor.chain().focus().redo().run()"
        :disabled="!editor.can().chain().focus().redo().run()"
        class="p-1 disabled:text-gray-400"
      >
        <RedoIcon title="Redo" />
      </button>

      <button
        type="button"
        @click="editor.chain().focus().toggleStrike().run()"
        :class="{ 'bg-gray-200 rounded': editor.isActive('strike') }"
        class="p-1"
      >
        <b style="text-decoration: line-through; font-size: 23px; line-height: 1">S</b>
      </button>
    </div>

    <EditorContent
      :editor="editor"
      content-type="html"
      style="overflow-x: auto; width: 1010px; padding: 5px"
    />
  </div>
</template>
<style lang="scss">
/* Basic editor styles */
.tiptap {
  > * + * {
    margin-top: 0.75em;
  }

  ul,
  ol {
    padding: 0 1rem;
  }

  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #616161;
  }

  pre {
    background: #0d0d0d;
    color: #fff;
    font-family: 'JetBrainsMono', monospace;
    padding: 0.75rem 1rem;
    border-radius: 0.5rem;

    code {
      color: inherit;
      padding: 0;
      background: none;
      font-size: 0.8rem;
    }
  }

  img {
    max-width: 100%;
    height: auto;
  }

  blockquote {
    padding-left: 1rem;
    border-left: 2px solid rgba(#0d0d0d, 0.1);
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0d0d0d, 0.1);
    margin: 2rem 0;
  }
}
.ProseMirror {
  height: 550px;
  width: 1000px;
  outline: none;
}

.border-t {
  border-top-width: 10px;
}
.tiptap p.is-editor-empty:first-child::before {
  color: #a0a1a2;
  content: attr(data-placeholder);
  float: left;
  height: 0;
  pointer-events: none;
}
</style>
