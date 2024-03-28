<script setup>

$cookies.remove('jwtToken');

const router = inject('router')
router.push('/')
//window.location.href = '/login'


</script>
